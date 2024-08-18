package com.server.homepage.controller;

import com.server.homepage.services.*;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import com.server.homepage.services.Exceptions.ProjectNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TitleService titleService;

    //login
    @PostMapping("/login")
    public String login(String password, HttpServletRequest request){
        Boolean correct = userService.checkPassword(password);
        if(correct == null)
            return "redirect:/admin?noAdmin";
        if(correct){
            request.getSession().setAttribute("admin", true);
            return "redirect:/admin";
        }
        return "redirect:/admin?notLoggedIn&wrongPassword";
    }

    //Add a project
    @PostMapping("/addProject")
    public String addProject(String text, String href, String description, HttpServletRequest request){
        try {
            projectService.addProject(text, href, description, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Delete a project
    @PostMapping("/deleteProject")
    public String deleteProject(Integer id, HttpServletRequest request){
        try {
            projectService.deleteProject(id, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Change project description
    @PostMapping("/changeProjectDescription")
    public String changeProjectDescription(Integer id, String description, HttpServletRequest request){
        try {
            projectService.updateProject(id, null, null, description, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        catch (ProjectNotFound e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        return "redirect:/admin";
    }

    //Add a social
    @PostMapping("/addSocial")
    public String addSocial(String text, String href, @RequestParam(required = false) String icon, HttpServletRequest request){
        try {
            socialService.addSocial(text, href, icon, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Delete a social
    @PostMapping("/deleteSocial")
    public String deleteSocial(Integer id, HttpServletRequest request){
        try {
            socialService.deleteSocial(id, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Change the image
    @PostMapping("/changeImage")
    public String changeImage(MultipartFile image, HttpServletRequest request){
        try {
            imageService.setImage(image, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        catch (IOException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image could not be processed");
        }
        return "redirect:/admin";
    }

    //Change the favicon
    @PostMapping("/changeFavicon")
    public String changeFavicon(MultipartFile favicon, HttpServletRequest request){
        try {
            imageService.setFavicon(favicon, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        catch (IOException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favicon could not be processed");
        }
        return "redirect:/admin";
    }

    //Change the Title
    @PostMapping("/changeTitle")
    public String changeTitle(String title, HttpServletRequest request){
        try {
            titleService.setTitle(title, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Change the projects title
    @PostMapping("/changeProjectsTitle")
    public String changeProjectsTitle(String projectsTitle, HttpServletRequest request){
        try {
            titleService.setProjectsTitle(projectsTitle, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

    //Change the projects description
    @PostMapping("/changeProjectsDescription")
    public String changeProjectsDescription(String projectsDescription, HttpServletRequest request){
        try {
            titleService.setProjectsDescription(projectsDescription, request);
        } catch (NotLoggedIn e) {
            return "redirect:/admin?notLoggedIn";
        }
        return "redirect:/admin";
    }

}
