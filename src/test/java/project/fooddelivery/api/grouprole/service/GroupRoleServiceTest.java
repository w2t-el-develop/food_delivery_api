package project.fooddelivery.api.grouprole.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.fooddelivery.api.grouprole.dto.RoleDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupRoleServiceTest {

    @Autowired
    private GroupRoleService groupRoleService;

    @Test
    @DisplayName("Should return all roles list successfully")
    void shouldReturnAllRoles() {
        List<RoleDTO> roles = groupRoleService.getAllRoles();
        
        assertThat(roles).isNotNull();
    }
}