package com.server.homepage.services;

import com.server.homepage.entities.Admin;
import com.server.homepage.repositories.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

public class UserServiceImplementation implements UserService{
    @Autowired
    private AdminRepository adminRepository;

    /**
     * checks if the password is correct
     * @param password the password of the user
     * @return true if the password is correct, false if not, null if there is no admin
     */
    @Override
    public Boolean checkPassword(String password) {
        Optional<Admin> optionalAdmin = adminRepository.findById(0);
        if(optionalAdmin.isEmpty())
            return null;
        Admin admin = optionalAdmin.get();
        return BCrypt.checkpw(password, admin.getPassword());
    }

    /**
     * checks if the user is an admin
     *
     * @param request the request
     * @return true if the user is an admin, false if not
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        return (request.getSession().getAttribute("admin") != null && request.getSession().getAttribute("admin").equals(true));
    }
}
