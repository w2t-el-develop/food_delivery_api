package project.fooddelivery.api.grouprole.repository;

import project.fooddelivery.api.grouprole.entity.GroupRole;
import project.fooddelivery.api.grouprole.entity.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, String> {

    @Modifying
    @Query("DELETE FROM GroupRole gr WHERE gr.groupId = :groupId")
    void deleteByGroupId(@Param("groupId") UUID groupId);

    @Query("SELECT r FROM role r JOIN GroupRole gr ON r.roleId = gr.roleId WHERE gr.groupId = :groupId")
    List<role> findRolesByGroupId(@Param("groupId") UUID groupId);
    
    boolean existsByGroupIdAndRoleId(UUID groupId, String roleId);
    
    void deleteByGroupIdAndRoleId(UUID groupId, String roleId);
}