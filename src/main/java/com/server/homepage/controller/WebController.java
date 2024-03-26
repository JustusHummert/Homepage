package com.server.homepage.controller;
import com.server.homepage.entities.Image;
import com.server.homepage.repositories.ImageRepository;
import com.server.homepage.repositories.ProjectRepository;
import com.server.homepage.repositories.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.Optional;

@Controller
public class WebController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private ImageRepository imageRepository;

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

    @GetMapping("/image")
    public ResponseEntity<byte[]> image() {
        Optional<Image> optionalImage = imageRepository.findById(0);
        if (optionalImage.isEmpty())
            return ResponseEntity.notFound().build();
        Image image = optionalImage.get();
        byte[] decodedImage = Base64.getDecoder().decode(image.getImage());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, image.getMediaType());
        return new ResponseEntity<>(decodedImage, headers, HttpStatus.OK);
    }
}
