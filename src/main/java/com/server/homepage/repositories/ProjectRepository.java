package com.server.homepage.repositories;

import com.server.homepage.entities.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
    void deleteByText(String text);
    Iterable<Project> findByText(String text);
}
