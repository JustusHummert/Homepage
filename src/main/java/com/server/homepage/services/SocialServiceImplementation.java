package com.server.homepage.services;

import com.server.homepage.entities.Social;
import com.server.homepage.repositories.SocialRepository;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialServiceImplementation implements SocialService{

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private UserService userService;

    /**
     * Adds a social to the database
     *
     * @param text the text of the social
     * @param href the href of the social
     * @param icon the icon of the social
     */
    @Override
    public void addSocial(String text, String href, String icon, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        socialRepository.save(new Social(text, href, icon));
    }

    /**
     * Deletes a social from the database
     *
     * @param id the id of the social
     */
    @Override
    public void deleteSocial(Integer id, HttpServletRequest request) throws NotLoggedIn{
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        socialRepository.deleteById(id);
    }
}
