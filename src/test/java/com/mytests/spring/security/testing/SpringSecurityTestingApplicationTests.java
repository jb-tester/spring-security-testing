package com.mytests.spring.security.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityTestingApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "irina", password = "qwerty", roles = {"USER"})
    void testUserPagesWithAuthorizedUser() throws Exception {
        mockMvc.perform(get("/users/irina"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("for user irina"))
        ;
    }

    //https://youtrack.jetbrains.com/issue/IDEA-250984 - fixed
    @Test
    @WithMockUser(username = "superuser", password = "qwerty", roles = {"VIP"})
    void testAdminPagesWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/adm"))
                .andDo(print())
                .andExpect(status().is(403))
        ;
    }

    // https://youtrack.jetbrains.com/issue/IDEA-292712 - fixed
    @Test
    @WithMockUser(username = "potus", password = "qwerty", authorities = {"ROLE_VIP"})
    void testVipPagesWithAuthorizedUser() throws Exception {
        mockMvc.perform(get("/vip/joe"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("for VIP user joe"))
        ;
    }

    // https://youtrack.jetbrains.com/issue/IDEA-158099
    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsService", value = "fbi")
    void testUserDetails() throws Exception {
        mockMvc.perform(get("/users/fbi"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("for user fbi"));
    }
}
