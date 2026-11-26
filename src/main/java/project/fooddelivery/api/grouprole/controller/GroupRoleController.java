package project.fooddelivery.api.grouprole.controller;

import project.fooddelivery.api.grouprole.dto.GroupRolesResponseDTO;
import project.fooddelivery.api.grouprole.dto.RoleDTO;
import project.fooddelivery.api.grouprole.service.GroupRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class GroupRoleController {

    private final GroupRoleService groupRoleService;

    public GroupRoleController(GroupRoleService groupRoleService) {
        this.groupRoleService = groupRoleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> viewAllRoles() {
        return ResponseEntity.ok(groupRoleService.getAllRoles());
    }

    @GetMapping("/groups/{groupId}/roles")
    public ResponseEntity<GroupRolesResponseDTO> viewRulesAssignedToGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupRoleService.getRolesAssignedToGroup(groupId));
    }

    @PostMapping("/groups/{groupId}/roles")
    public ResponseEntity<Void> assignRolesToGroup(@PathVariable UUID groupId, @RequestParam String roleId) {
        groupRoleService.assignRoleToGroup(groupId, roleId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/groups/{groupId}/roles")
    public ResponseEntity<GroupRolesResponseDTO> updateRuleOfGroup(
            @PathVariable UUID groupId,
            @RequestBody List<String> roleIds) {
        GroupRolesResponseDTO response = groupRoleService.updateGroupRoles(groupId, roleIds);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/groups/{groupId}/roles/{roleId}")
    public ResponseEntity<Void> deleteRoleFromGroup(@PathVariable UUID groupId, @PathVariable String roleId) {
        groupRoleService.deleteRoleFromGroup(groupId, roleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<Void> assignUserToGroup(@PathVariable UUID groupId, @PathVariable UUID userId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable UUID groupId, @PathVariable UUID userId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/group")
    public ResponseEntity<Void> updateUserGroup(@PathVariable UUID userId, @RequestParam UUID newGroupId) {
        return ResponseEntity.ok().build();
    }
}