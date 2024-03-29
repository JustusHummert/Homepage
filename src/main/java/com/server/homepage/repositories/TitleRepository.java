package com.server.homepage.repositories;

import com.server.homepage.entities.Title;
import org.springframework.data.repository.CrudRepository;

public interface TitleRepository extends CrudRepository<Title, Integer>{
}
