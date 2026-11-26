package project.fooddelivery.api.grouprole.service;

import project.fooddelivery.api.grouprole.dto.GroupRolesResponseDTO;
import project.fooddelivery.api.grouprole.dto.RoleDTO;
import project.fooddelivery.api.grouprole.entity.GroupRole;
import project.fooddelivery.api.grouprole.entity.role;
import project.fooddelivery.api.grouprole.repository.GroupRepository;
import project.fooddelivery.api.grouprole.repository.GroupRoleRepository;
import project.fooddelivery.api.grouprole.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupRoleService {

    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final GroupRoleRepository groupRoleRepository;

    public GroupRoleService(GroupRepository groupRepository, RoleRepository roleRepository, GroupRoleRepository groupRoleRepository) {
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.groupRoleRepository = groupRoleRepository;
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public GroupRolesResponseDTO getRolesAssignedToGroup(UUID groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));
        
        List<role> roles = groupRoleRepository.findRolesByGroupId(groupId);
        List<RoleDTO> roleDTOs = roles.stream().map(RoleDTO::fromEntity).collect(Collectors.toList());
        return new GroupRolesResponseDTO(groupId, roleDTOs);
    }

    @Transactional
    public void assignRoleToGroup(UUID groupId, String roleId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));
        roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));

        if (!groupRoleRepository.existsByGroupIdAndRoleId(groupId, roleId)) {
            groupRoleRepository.save(new GroupRole(groupId, roleId));
        }
    }

    @Transactional
    public GroupRolesResponseDTO updateGroupRoles(UUID groupId, List<String> roleIds) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        List<String> sanitizedRoleIds = (roleIds == null) ? Collections.emptyList() :
                roleIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<role> existingRoles = Collections.emptyList();
        if (!sanitizedRoleIds.isEmpty()) {
            existingRoles = roleRepository.findAllById(sanitizedRoleIds);
            if (existingRoles.size() != sanitizedRoleIds.size()) {
                throw new IllegalArgumentException("One or more Role IDs are invalid.");
            }
        }

        groupRoleRepository.deleteByGroupId(groupId);

        if (!existingRoles.isEmpty()) {
            List<GroupRole> newGroupRoles = existingRoles.stream()
                    .map(role -> new GroupRole(groupId, role.getRoleId()))
                    .collect(Collectors.toList());
            groupRoleRepository.saveAll(newGroupRoles);
        }

        List<RoleDTO> updatedRoleDTOs = existingRoles.stream()
                .map(RoleDTO::fromEntity)
                .collect(Collectors.toList());

        return new GroupRolesResponseDTO(groupId, updatedRoleDTOs);
    }

    @Transactional
    public void deleteRoleFromGroup(UUID groupId, String roleId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));
        
        if (groupRoleRepository.existsByGroupIdAndRoleId(groupId, roleId)) {
            groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
        }
    }
}