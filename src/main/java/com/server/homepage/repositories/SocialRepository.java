package com.server.homepage.repositories;

import com.server.homepage.entities.Project;
import com.server.homepage.entities.Social;
import org.springframework.data.repository.CrudRepository;

public interface SocialRepository extends CrudRepository<Social, Integer>{
    void deleteByText(String text);
    Iterable<Social> findByText(String text);
}
