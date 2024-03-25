package com.server.homepage.controller;

import com.server.homepage.entities.Project;
import com.server.homepage.repositories.AdminRepository;
import com.server.homepage.entities.Admin;
import com.server.homepage.repositories.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProjectRepository projectRepository;

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
}
