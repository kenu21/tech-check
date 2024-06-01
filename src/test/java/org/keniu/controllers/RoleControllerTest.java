package org.keniu.controllers;

import org.junit.jupiter.api.Test;
import org.keniu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testSetRole() throws Exception {
        String userLogin = "user1";
        String roleName = "admin";

        mockMvc.perform(MockMvcRequestBuilders.post("/set-role")
                        .param("user_login", userLogin)
                        .param("role_name", roleName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("set Role"));
    }
}
