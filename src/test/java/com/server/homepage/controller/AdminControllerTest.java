package com.server.homepage.controller;

import com.server.homepage.entities.*;
import com.server.homepage.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SocialRepository socialRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private TitleRepository titleRepository;

    @Test
    void login() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //no admin
        adminRepository.deleteAll();
        mvc.perform(post("/admin/login")
                .param("password", "password")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?noAdmin"));
        //Add an admin
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        adminRepository.save(new Admin(hashedPassword));
        //wrong password
        mvc.perform(post("/admin/login")
                .param("password", "wrong")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn&wrongPassword"));
        //correct password
        mvc.perform(post("/admin/login")
                .param("password", "password")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
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
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/addProject")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        projectRepository.deleteByText("text");
    }

    @Test
    void deleteProject() throws Exception{
        MockHttpSession session = new MockHttpSession();
        projectRepository.save(new Project("text", "href", "description"));
        Integer id = projectRepository.findByText("text").iterator().next().getId();
        //not logged in
        mvc.perform(post("/admin/deleteProject")
                .param("id", id.toString())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/deleteProject")
                .param("id", id.toString())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void changeProjectDescription() throws Exception{
        MockHttpSession session = new MockHttpSession();
        projectRepository.save(new Project("text", "href", "description"));
        Integer id = projectRepository.findByText("text").iterator().next().getId();
        //not logged in
        mvc.perform(post("/admin/changeProjectDescription")
                .param("id", id.toString())
                .param("description", "new description")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectDescription")
                .param("id", id.toString())
                .param("description", "new description")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Project> optionalProject = projectRepository.findById(id);
        assertTrue(optionalProject.isPresent());
        Project project = optionalProject.get();
        assertEquals("new description", project.getDescription());
        projectRepository.delete(project);
    }

    @Test
    @Transactional
    void addSocial() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/addSocial")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/addSocial")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        socialRepository.deleteByText("text");
    }

    @Test
    void deleteSocial() throws Exception{
        MockHttpSession session = new MockHttpSession();
        socialRepository.save(new Social("text", "href", "icon"));
        Integer id = socialRepository.findByText("text").iterator().next().getId();
        //not logged in
        mvc.perform(post("/admin/deleteSocial")
                .param("id", id.toString())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/deleteSocial")
                .param("id", id.toString())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void changeImage() throws Exception{
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", "image".getBytes());
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(multipart("/admin/changeImage")
                        .file("image", file.getBytes())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(multipart("/admin/changeImage")
                .file("image", file.getBytes())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Image> optionalNewImage = imageRepository.findById(0);
        assertTrue(optionalNewImage.isPresent());
        Image newImage = optionalNewImage.get();
        imageRepository.deleteAll();
    }

    @Test
    void changeFavicon() throws Exception{
        MockHttpSession session = new MockHttpSession();
        MockMultipartFile file = new MockMultipartFile("favicon", "favicon", "image/x-icon", "favicon".getBytes());
        //not logged in
        mvc.perform(multipart("/admin/changeFavicon")
                .file("favicon", file.getBytes())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(multipart("/admin/changeFavicon")
                .file("favicon", file.getBytes())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Image> optionalNewImage = imageRepository.findById(0);
        assertTrue(optionalNewImage.isPresent());
        Image newImage = optionalNewImage.get();
        imageRepository.deleteAll();
    }

    @Test
    void changeTitle() throws Exception{
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeTitle")
                .param("title", "title")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeTitle")
                .param("title", "title")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("title", newTitle.getTitle());
        titleRepository.deleteAll();
    }

    @Test
    void changeProjectsTitle() throws Exception{
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeProjectsTitle")
                .param("projectsTitle", "projectsTitle")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectsTitle")
                .param("projectsTitle", "projectsTitle")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("projectsTitle", newTitle.getProjectsTitle());
        titleRepository.deleteAll();
    }

    @Test
    void changeProjectsDescription() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeProjectsDescription")
                        .param("projectsDescription", "projectsDescription")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin?notLoggedIn"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectsDescription")
                        .param("projectsDescription", "projectsDescription")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("projectsDescription", newTitle.getProjectsDescription());
        titleRepository.deleteAll();
    }
}