package com.server.homepage.controller;

import com.server.homepage.entities.*;
import com.server.homepage.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @Autowired
    private SocialRepository socialRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private TitleRepository titleRepository;

    //10 * 10 png with black rectangle
    private static final String testPicture = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAACXBIWXMAAA7DAAAOwwHHb6hkAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAEFJREFUGJVjZIAAIQYGBm4G7OAnAwPDKxhnAQMDw38c+DADAwMDEw5TMMAAKmSE0uYMDAyKONS8ZmBg2EusgTQAAPeUC2hoB/iaAAAAAElFTkSuQmCC";
    private static final String testPictureWithoutHeader = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAACXBIWXMAAA7DAAAOwwHHb6hkAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAEFJREFUGJVjZIAAIQYGBm4G7OAnAwPDKxhnAQMDw38c+DADAwMDEw5TMMAAKmSE0uYMDAyKONS8ZmBg2EusgTQAAPeUC2hoB/iaAAAAAElFTkSuQmCC";

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
        projectRepository.save(new Project("text", "href", "description"));
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
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectDescription")
                .param("id", id.toString())
                .param("description", "new description")
                .session(session))
                .andExpect(content().string("changed"));
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
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/addSocial")
                .param("text", "text")
                .param("href", "href")
                .session(session))
                .andExpect(content().string("added"));
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
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/deleteSocial")
                .param("id", id.toString())
                .session(session))
                .andExpect(content().string("deleted"));
    }

    @Test
    void changeImage() throws Exception{
        Optional<Image> oldImage = imageRepository.findById(0);
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeImage")
                .param("image", "image")
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeImage")
                .param("image", testPicture)
                .session(session))
                .andExpect(content().string("changed"));
        Optional<Image> optionalNewImage = imageRepository.findById(0);
        assertTrue(optionalNewImage.isPresent());
        Image newImage = optionalNewImage.get();
        String image = Arrays.toString(Base64.getDecoder().decode(testPictureWithoutHeader));
        assertEquals(image, Arrays.toString(newImage.getImage()));
        assertEquals("image/png", newImage.getMediaType());
        imageRepository.deleteAll();
        oldImage.ifPresent(imageEntity -> imageRepository.save(imageEntity));
    }

    @Test
    void changeFavicon() throws Exception{
        Optional<Image> oldImage = imageRepository.findById(0);
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeFavicon")
                .param("favicon", testPicture)
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeFavicon")
                .param("favicon", testPicture)
                .session(session))
                .andExpect(content().string("changed"));
        Optional<Image> optionalNewImage = imageRepository.findById(0);
        assertTrue(optionalNewImage.isPresent());
        Image newImage = optionalNewImage.get();
        String favicon = Arrays.toString(Base64.getDecoder().decode(testPictureWithoutHeader));
        assertEquals(favicon, Arrays.toString(newImage.getFavicon()));
        imageRepository.deleteAll();
        oldImage.ifPresent(image -> imageRepository.save(image));
    }

    @Test
    void changeTitle() throws Exception{
        Optional<Title> oldTitle = titleRepository.findById(0);
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeTitle")
                .param("title", "title")
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeTitle")
                .param("title", "title")
                .session(session))
                .andExpect(content().string("changed"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("title", newTitle.getTitle());
        titleRepository.deleteAll();
        oldTitle.ifPresent(title -> titleRepository.save(title));
    }

    @Test
    void changeProjectsTitle() throws Exception{
        Optional<Title> oldTitle = titleRepository.findById(0);
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeProjectsTitle")
                .param("projectsTitle", "projectsTitle")
                .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectsTitle")
                .param("projectsTitle", "projectsTitle")
                .session(session))
                .andExpect(content().string("changed"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("projectsTitle", newTitle.getProjectsTitle());
        titleRepository.deleteAll();
        oldTitle.ifPresent(title -> titleRepository.save(title));
    }

    @Test
    void changeProjectsDescription() throws Exception {
        Optional<Title> oldTitle = titleRepository.findById(0);
        MockHttpSession session = new MockHttpSession();
        //not logged in
        mvc.perform(post("/admin/changeProjectsDescription")
                        .param("projectsDescription", "projectsDescription")
                        .session(session))
                .andExpect(content().string("not logged in"));
        //logged in
        session.setAttribute("admin", true);
        mvc.perform(post("/admin/changeProjectsDescription")
                        .param("projectsDescription", "projectsDescription")
                        .session(session))
                .andExpect(content().string("changed"));
        Optional<Title> optionalNewTitle = titleRepository.findById(0);
        assertTrue(optionalNewTitle.isPresent());
        Title newTitle = optionalNewTitle.get();
        assertEquals("projectsDescription", newTitle.getProjectsDescription());
        titleRepository.deleteAll();
        oldTitle.ifPresent(title -> titleRepository.save(title));
    }
}