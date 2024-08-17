package com.server.homepage.services;

import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;

public interface ImageService {

    /**
     * Sets the image
     * @param image the image as a base64 string
     * @param request the request
     */
    public void setImage(String image, HttpServletRequest request) throws NotLoggedIn;

    /**
     * Sets the favicon
     * @param favicon the favicon as a base64 string
     * @param request the request
     */
    public void setFavicon(String favicon, HttpServletRequest request) throws NotLoggedIn;
}
