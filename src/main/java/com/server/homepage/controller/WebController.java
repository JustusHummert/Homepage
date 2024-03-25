package com.server.homepage.controller;
import com.server.homepage.repositories.ProjectRepository;
import com.server.homepage.repositories.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SocialRepository socialRepository;

    @GetMapping("")
    public String homepage(Model model){
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("socials", socialRepository.findAll());
        return "homepage";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("socials", socialRepository.findAll());
        return "admin";
    }
}
