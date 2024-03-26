package com.server.homepage.repositories;

import com.server.homepage.entities.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Integer>{
}
