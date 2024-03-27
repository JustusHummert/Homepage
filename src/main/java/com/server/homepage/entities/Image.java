package com.server.homepage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.http.MediaType;

@Entity
public class Image {
    //ID of the only image
    @Id
    private Integer id = 0;

    //The image shown on the homepage
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    //The Media type of the image
    private String mediaType;

    public Image() {
    }

    public Image(String image, String mediaType) {
        this.image = image;
        this.mediaType = mediaType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
