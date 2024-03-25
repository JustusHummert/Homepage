package com.server.homepage.controller;

import com.server.homepage.entities.Project;
import com.server.homepage.entities.Social;
import com.server.homepage.repositories.AdminRepository;
import com.server.homepage.entities.Admin;
import com.server.homepage.repositories.ProjectRepository;
import com.server.homepage.repositories.SocialRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public @ResponseBody String addProject(@ModelAttribute("admin") boolean admin, String text, String href){
        if(!admin)
            return "not logged in";
        projectRepository.save(new Project(text, href));
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
}
