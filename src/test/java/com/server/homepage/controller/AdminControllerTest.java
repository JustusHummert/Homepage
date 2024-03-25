package com.server.homepage.controller;

import com.server.homepage.entities.Admin;
import com.server.homepage.entities.Project;
import com.server.homepage.repositories.AdminRepository;
import com.server.homepage.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void login() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //no admin
        adminRepository.deleteAll();
        mvc.perform(post("/admin/login")
                .param("password", "password")
                .session(session))
                .andExpect(content().string("no Admin"));
        //Add an admin
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        adminRepository.save(new Admin(hashedPassword));
        //wrong password
        mvc.perform(post("/admin/login")
                .param("password", "wrong")
                .session(session))
                .andExpect(content().string("wrong password"));
        //correct password
        mvc.perform(post("/admin/login")
                .param("password", "password")
                .session(session))
                .andExpect(content().string("logged in"));
    }

    @Test
    @Transactional
    void addProject() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/addProject")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/addProject")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(content().string("added"));
        projectRepository.deleteByText("text");
    }

    @Test
    void deleteProject() throws Exception{
        MockHttpSession session = new MockHttpSession();
        projectRepository.save(new Project("text", "href"));
        Integer id = projectRepository.findByText("text").iterator().next().getId();
        //not logged in
        mvc.perform(post("/admin/deleteProject")
                .param("id", id.toString())
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/deleteProject")
                .param("id", id.toString())
                .session(session))
                .andExpect(content().string("deleted"));
    }
}