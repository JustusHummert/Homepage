package com.server.homepage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Title {
    //ID of the only title
    @Id
    private Integer id = 0;

    //The title of the homepage
    private String title;

    //The title of the projects
    private String projectsTitle;

    public Title() {
    }

    public Title(String title, String projectsTitle) {
        this.title = title;
        this.projectsTitle = projectsTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectsTitle() {
        return projectsTitle;
    }

    public void setProjectsTitle(String projectsTitle) {
        this.projectsTitle = projectsTitle;
    }
}
