package com.server.homepage.services;

import com.server.homepage.entities.Project;
import com.server.homepage.repositories.ProjectRepository;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import com.server.homepage.services.Exceptions.ProjectNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    /**
     * Adds a project to the database
     *
     * @param text        the text of the project
     * @param href        the href of the project
     * @param description the description of the project
     * @param request     the request
     */
    @Override
    public void addProject(String text, String href, String description, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        projectRepository.save(new Project(text, href, description));
    }

    /**
     * Deletes a project from the database
     *
     * @param id      the id of the project
     * @param request the request
     */
    @Override
    public void deleteProject(Integer id, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        projectRepository.deleteById(id);
    }

    /**
     * Updates a project in the database
     *
     * @param id          the id of the project
     * @param text        the text of the project
     * @param href        the href of the project
     * @param description the description of the project
     * @param request     the request
     */
    @Override
    public void updateProject(Integer id, String text, String href, String description, HttpServletRequest request) throws NotLoggedIn, ProjectNotFound {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isEmpty())
            throw new ProjectNotFound();
        Project project = optionalProject.get();
        if(text != null)
            project.setText(text);
        if(href != null)
            project.setHref(href);
        if(description != null)
            project.setDescription(description);
        projectRepository.save(project);
    }
}
