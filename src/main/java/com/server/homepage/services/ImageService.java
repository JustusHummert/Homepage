package com.server.homepage.services;

import com.server.homepage.entities.Image;
import com.server.homepage.services.Exceptions.ImageNotFound;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    /**
     * Sets the image
     * @param image the image as a base64 string
     * @param request the request
     */
    public void setImage(MultipartFile image, HttpServletRequest request) throws NotLoggedIn, IOException;

    /**
     * Sets the favicon
     * @param favicon the favicon as a base64 string
     * @param request the request
     */
    public void setFavicon(MultipartFile favicon, HttpServletRequest request) throws NotLoggedIn, IOException;

    /**
     * Gets the image
     * @return the image
     */
    public Image getImage() throws ImageNotFound;
}
