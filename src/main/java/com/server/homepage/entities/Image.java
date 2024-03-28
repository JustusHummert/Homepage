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
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    //The favicon of the homepage
    @Column(columnDefinition = "LONGBLOB")
    private byte[] favicon;

    //The Media type of the image
    private String mediaType;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getFavicon() {
        return favicon;
    }

    public void setFavicon(byte[] favicon) {
        this.favicon = favicon;
    }
}
