package project.fooddelivery.api.grouprole.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "group_role")
public class GroupRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_role_id")
    private String groupRoleId;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    public GroupRole() {}

    public GroupRole(UUID groupId, String roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }

    public String getGroupRoleId() { return groupRoleId; }
    public void setGroupRoleId(String groupRoleId) { this.groupRoleId = groupRoleId; }
    public UUID getGroupId() { return groupId; }
    public void setGroupId(UUID groupId) { this.groupId = groupId; }
    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }
}