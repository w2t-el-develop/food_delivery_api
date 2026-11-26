package project.fooddelivery.api.grouprole.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.fooddelivery.api.grouprole.service.GroupRoleService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupRoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupRoleService groupRoleService;

    @InjectMocks
    private GroupRoleController groupRoleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupRoleController).build();
    }

    @Test
    @DisplayName("Should return 200 OK when fetching all roles endpoint is called")
    void shouldReturnAllRoles() throws Exception {
        when(groupRoleService.getAllRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk());
    }
}