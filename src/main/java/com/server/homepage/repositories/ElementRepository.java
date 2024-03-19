package com.server.homepage.repositories;

import com.server.homepage.entities.Element;
import org.springframework.data.repository.CrudRepository;

public interface ElementRepository extends CrudRepository<Element, Integer>{
    void deleteByText(String text);
    Iterable<Element> findByText(String text);
}
