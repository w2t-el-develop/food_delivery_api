package project.fooddelivery.api.grouprole.dto;

import project.fooddelivery.api.grouprole.entity.role;

public class RoleDTO {
    private String roleId;
    private String roleName;
    private String roleDescription;

    public static RoleDTO fromEntity(role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        dto.setRoleDescription(role.getRoleDescription());
        return dto;
    }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getRoleDescription() { return roleDescription; }
    public void setRoleDescription(String roleDescription) { this.roleDescription = roleDescription; }
}