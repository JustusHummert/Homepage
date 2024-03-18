package com.server.homepage.controller;

import com.server.homepage.entities.Element;
import com.server.homepage.repositories.AdminRepository;
import com.server.homepage.entities.Admin;
import com.server.homepage.repositories.ElementRepository;
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
    private ElementRepository elementRepository;

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
            return "logged in";
        }
        return "wrong password";
    }

    //Add an element
    @PostMapping("/addElement")
    public @ResponseBody String addElement(@ModelAttribute("admin") boolean admin, String text, String href){
        if(!admin)
            return "not logged in";
        elementRepository.save(new Element(text, href));
        return "added";
    }

    //Delete an element
    @PostMapping("/deleteElement")
    public @ResponseBody String deleteElement(@ModelAttribute("admin") boolean admin, Integer id){
        if(!admin)
            return "not logged in";
        elementRepository.deleteById(id);
        return "deleted";
    }
}
