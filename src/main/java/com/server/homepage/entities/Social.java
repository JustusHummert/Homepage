package com.server.homepage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Social {

    @Id
    @GeneratedValue
    private Integer id;

    //The visible text of the social
    private String text;

    //The href of the social
    private String href;

    //The icon of the social as html element
    private String icon;

    public Social() {
    }

    public Social(String text, String href, String icon) {
        this.text = text;
        this.href = href;
        this.icon = icon;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
