package com.server.homepage.services;

import com.server.homepage.services.Exceptions.NotLoggedIn;
import com.server.homepage.services.Exceptions.ProjectNotFound;
import jakarta.servlet.http.HttpServletRequest;

public interface ProjectService {

    /**
     * Adds a project to the database
     * @param text the text of the project
     * @param href the href of the project
     * @param description the description of the project
     * @param request the request
     */
    public void addProject(String text, String href, String description, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Deletes a project from the database
     * @param id the id of the project
     * @param request the request
     */
    public void deleteProject(Integer id, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Updates a project in the database
     * @param id the id of the project
     * @param text the text of the project
     * @param href the href of the project
     * @param description the description of the project
     * @param request the request
     */
    public void updateProject(Integer id, String text, String href, String description, HttpServletRequest request) throws NotLoggedIn, ProjectNotFound;
}
