package com.server.homepage.services;

import com.server.homepage.entities.Image;
import com.server.homepage.repositories.ImageRepository;
import com.server.homepage.services.Exceptions.ImageNotFound;
import com.server.homepage.services.Exceptions.NotLoggedIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
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
    public void setImage(MultipartFile image, HttpServletRequest request) throws NotLoggedIn, IOException {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        //decode the image
        byte[] decodedImage = image.getBytes();
        //extract the media type
        String mediaType = image.getContentType();
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
    public void setFavicon(MultipartFile favicon, HttpServletRequest request) throws NotLoggedIn, IOException {
        if(!userService.isAdmin(request))
            throw new NotLoggedIn();
        //decode the favicon
        byte[] decodedFavicon = favicon.getBytes();
        //save the favicon
        Optional<Image> optionalImage = imageRepository.findById(0);
        Image imageEntity = optionalImage.orElseGet(Image::new);
        imageEntity.setFavicon(decodedFavicon);
        imageRepository.save(imageEntity);
    }

    /**
     * Gets the image
     *
     * @return the image
     */
    @Override
    public Image getImage() throws ImageNotFound {
        Optional<Image> optionalImage = imageRepository.findById(0);
        if(optionalImage.isEmpty())
            throw new ImageNotFound();
        return optionalImage.get();
    }
}
