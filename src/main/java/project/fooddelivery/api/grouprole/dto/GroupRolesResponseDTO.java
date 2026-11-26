package project.fooddelivery.api.grouprole.dto;

import java.util.List;
import java.util.UUID;

public class GroupRolesResponseDTO {
    private UUID groupId;
    private List<RoleDTO> roles;

    public GroupRolesResponseDTO(UUID groupId, List<RoleDTO> roles) {
        this.groupId = groupId;
        this.roles = roles;
    }

    public UUID getGroupId() { return groupId; }
    public void setGroupId(UUID groupId) { this.groupId = groupId; }
    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> roles) { this.roles = roles; }
}