package com.server.homepage.controller;
import com.server.homepage.entities.Image;
import com.server.homepage.entities.Title;
import com.server.homepage.repositories.ImageRepository;
import com.server.homepage.repositories.ProjectRepository;
import com.server.homepage.repositories.SocialRepository;
import com.server.homepage.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class WebController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TitleRepository titleRepository;

    @GetMapping("")
    public String homepage(Model model){
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("socials", socialRepository.findAll());
        model.addAttribute("title", titleRepository.findById(0).orElse(new Title("no title set", "no projects title set", "no projects description set")));
        return "homepage";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("socials", socialRepository.findAll());
        model.addAttribute("title", titleRepository.findById(0).orElse(new Title("no title set", "no projects title set", "no projects description set")));
        return "admin";
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> image() {
        Optional<Image> optionalImage = imageRepository.findById(0);
        if (optionalImage.isEmpty())
            return ResponseEntity.notFound().build();
        Image image = optionalImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, image.getMediaType());
        return new ResponseEntity<>(image.getImage(), headers, HttpStatus.OK);
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<byte[]> favicon(){
        Optional<Image> optionalImage = imageRepository.findById(0);
        if (optionalImage.isEmpty())
            return ResponseEntity.notFound().build();
        Image image = optionalImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/x-icon");
        return new ResponseEntity<>(image.getFavicon(), headers, HttpStatus.OK);
    }
}
