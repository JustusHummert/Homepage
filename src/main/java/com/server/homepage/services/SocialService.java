package com.server.homepage.services;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;

public interface SocialService {
    /**
     * Adds a social to the database
     * @param text the text of the social
     * @param href the href of the social
     * @param icon the icon of the social
     */
    public void addSocial(String text, String href, String icon, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Deletes a social from the database
     * @param id the id of the social
     */
    public void deleteSocial(Integer id, HttpServletRequest request) throws NotLoggedIn;
}
