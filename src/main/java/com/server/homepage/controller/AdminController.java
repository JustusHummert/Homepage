package com.server.homepage.controller;

import com.server.homepage.entities.*;
import com.server.homepage.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
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

    //check if the admin is logged in
    @ModelAttribute("admin")
    public boolean isAdmin(HttpServletRequest request){
        return (request.getSession().getAttribute("admin") != null && request.getSession().getAttribute("admin").equals(true));
    }

    //login
    @PostMapping("/login")
    public @ResponseBody String login(String password, HttpServletRequest request){
        Optional<Admin> optionalAdmin = adminRepository.findById(0);
        if(optionalAdmin.isEmpty())
            return "no Admin";
        Admin admin = optionalAdmin.get();
        if(BCrypt.checkpw(password, admin.getPassword())){
            request.getSession().setAttribute("admin", true);
            request.getSession().setMaxInactiveInterval(60*60);
            return "logged in";
        }
        return "wrong password";
    }

    //Add a project
    @PostMapping("/addProject")
    public @ResponseBody String addProject(@ModelAttribute("admin") boolean admin, String text, String href, String description){
        if(!admin)
            return "not logged in";
        projectRepository.save(new Project(text, href, description));
        return "added";
    }

    //Delete a project
    @PostMapping("/deleteProject")
    public @ResponseBody String deleteProject(@ModelAttribute("admin") boolean admin, Integer id){
        if(!admin)
            return "not logged in";
        projectRepository.deleteById(id);
        return "deleted";
    }

    //Change project description
    @PostMapping("/changeProjectDescription")
    public @ResponseBody String changeProjectDescription(@ModelAttribute("admin") boolean admin, Integer id, String description){
        if(!admin)
            return "not logged in";
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isEmpty())
            return "no project";
        Project project = optionalProject.get();
        project.setDescription(description);
        projectRepository.save(project);
        return "changed";
    }

    //Add a social
    @PostMapping("/addSocial")
    public @ResponseBody String addSocial(@ModelAttribute("admin") boolean admin, String text, String href,
                                          @RequestParam(required = false) String icon){
        if(!admin)
            return "not logged in";
        socialRepository.save(new Social(text, href, icon));
        return "added";
    }

    //Delete a social
    @PostMapping("/deleteSocial")
    public @ResponseBody String deleteSocial(@ModelAttribute("admin") boolean admin, Integer id){
        if(!admin)
            return "not logged in";
        socialRepository.deleteById(id);
        return "deleted";
    }

    //Change the image
    @PostMapping("/changeImage")
    public @ResponseBody String changeImage(@ModelAttribute("admin") boolean admin, String image){
        if(!admin)
            return "not logged in";
        //remove part before base64
        String imageString = image.substring(image.indexOf("base64,") + 7);
        byte[] decodedImage = Base64.getDecoder().decode(imageString);
        //extract the media type
        String mediaType = image.substring(image.indexOf("data:") + 5, image.indexOf(";"));
        Optional<Image> optionalImage = imageRepository.findById(0);
        Image imageEntity = optionalImage.orElseGet(Image::new);
        imageEntity.setImage(decodedImage);
        imageEntity.setMediaType(mediaType);
        imageRepository.save(imageEntity);
        return "changed";
    }

    //Change the favicon
    @PostMapping("/changeFavicon")
    public @ResponseBody String changeFavicon(@ModelAttribute("admin") boolean admin, String favicon){
        if(!admin)
            return "not logged in";
        //remove part before base64
        String faviconString = favicon.substring(favicon.indexOf("base64,") + 7);
        byte[] decodedFavicon = Base64.getDecoder().decode(faviconString);
        //extract the media type
        Optional<Image> optionalImage = imageRepository.findById(0);
        Image imageEntity = optionalImage.orElseGet(Image::new);
        imageEntity.setFavicon(decodedFavicon);
        imageRepository.save(imageEntity);
        return "changed";
    }

    //Change the Title
    @PostMapping("/changeTitle")
    public @ResponseBody String changeTitle(@ModelAttribute("admin") boolean admin, String title){
        if(!admin)
            return "not logged in";
        Optional<Title> optionalTitle = titleRepository.findById(0);
        Title titleEntity = optionalTitle.orElseGet(Title::new);
        titleEntity.setTitle(title);
        titleRepository.save(titleEntity);
        return "changed";
    }

    //Change the projects title
    @PostMapping("/changeProjectsTitle")
    public @ResponseBody String changeProjectsTitle(@ModelAttribute("admin") boolean admin, String projectsTitle) {
        if (!admin)
            return "not logged in";
        Optional<Title> optionalTitle = titleRepository.findById(0);
        Title titleEntity = optionalTitle.orElseGet(Title::new);
        titleEntity.setProjectsTitle(projectsTitle);
        titleRepository.save(titleEntity);
        return "changed";
    }



}
