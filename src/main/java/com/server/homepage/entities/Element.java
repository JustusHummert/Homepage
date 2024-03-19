package com.server.homepage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Element {

    @Id
    @GeneratedValue
    private Integer id;

    //The visible text of the element
    private String text;

    //The href of the element
    private String href;

    public Element() {
    }

    public Element(String text, String href) {
        this.text = text;
        this.href = href;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
