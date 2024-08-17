package com.server.homepage.services;

import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;

public interface TitleService {

    /**
     * Set the title of the homepage
     * @param title The title of the homepage
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    public void setTitle(String title, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Set the title of the projects section
     * @param title The title of the projects section
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    public void setProjectsTitle(String title, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Set the description of the projects section
     * @param description The description of the projects section
     * @param request The request
     * @throws NotLoggedIn If the user is not logged in
     */
    public void setProjectsDescription(String description, HttpServletRequest request) throws NotLoggedIn;
}
