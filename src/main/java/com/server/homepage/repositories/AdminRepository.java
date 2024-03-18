package com.server.homepage.repositories;

import com.server.homepage.entities.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
}
