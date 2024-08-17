package com.server.homepage.services;

import com.server.homepage.entities.Image;
import com.server.homepage.repositories.ImageRepository;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.Optional;

public class ImageServiceImplementation implements ImageService{

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Sets the image
     * @param image   the image as a base64 string
     * @param request the request
     */
    @Override
    public void setImage(String image, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        //remove part before base64
        String imageString = image.substring(image.indexOf("base64,") + 7);
        byte[] decodedImage = Base64.getDecoder().decode(imageString);
        //extract the media type
        String mediaType = image.substring(image.indexOf("data:") + 5, image.indexOf(";"));
        //save the image
        Optional<Image> optionalImage = imageRepository.findById(0);
        Image imageEntity = optionalImage.orElseGet(Image::new);
        imageEntity.setImage(decodedImage);
        imageEntity.setMediaType(mediaType);
        imageRepository.save(imageEntity);
    }

    /**
     * Sets the favicon
     * @param favicon the favicon as a base64 string
     * @param request the request
     */
    @Override
    public void setFavicon(String favicon, HttpServletRequest request) throws NotLoggedIn {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        //remove part before base64
        String faviconString = favicon.substring(favicon.indexOf("base64,") + 7);
        byte[] decodedFavicon = Base64.getDecoder().decode(faviconString);
        //save the favicon
        Optional<Image> optionalImage = imageRepository.findById(0);
        Image imageEntity = optionalImage.orElseGet(Image::new);
        imageEntity.setFavicon(decodedFavicon);
        imageRepository.save(imageEntity);
    }
}
