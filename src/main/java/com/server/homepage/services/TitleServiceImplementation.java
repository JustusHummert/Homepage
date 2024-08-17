package com.server.homepage.services;

import com.server.homepage.entities.Title;
import com.server.homepage.repositories.TitleRepository;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TitleServiceImplementation implements TitleService{
    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private UserService userService;

    /**
     * Set the title of the homepage
     * @param title The title of the homepage
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    @Override
    public void setTitle(String title, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request)){
            throw new NotLoggedIn();
        }
        Optional<Title> optionalTitle = titleRepository.findById(0);
        Title titleEntity = optionalTitle.orElseGet(Title::new);
        titleEntity.setTitle(title);
        titleRepository.save(titleEntity);
    }

    /**
     * Set the title of the projects section
     * @param title The title of the projects section
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    @Override
    public void setProjectsTitle(String title, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request)){
            throw new NotLoggedIn();
        }
        Optional<Title> optionalTitle = titleRepository.findById(0);
        Title titleEntity = optionalTitle.orElseGet(Title::new);
        titleEntity.setProjectsTitle(title);
        titleRepository.save(titleEntity);
    }

    /**
     * Set the description of the projects section
     * @param description The description of the projects section
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    @Override
    public void setProjectsDescription(String description, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request)){
            throw new NotLoggedIn();
        }
        Optional<Title> optionalTitle = titleRepository.findById(0);
        Title titleEntity = optionalTitle.orElseGet(Title::new);
        titleEntity.setProjectsDescription(description);
        titleRepository.save(titleEntity);
    }
}
