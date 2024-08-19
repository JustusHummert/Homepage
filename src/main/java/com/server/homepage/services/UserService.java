package com.server.homepage.services;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * checks if the password is correct
     * @param password the password of the user
     * @return true if the password is correct, false if not, null if there is no admin
     */
    public Boolean checkPassword(String password);

    /**
     * checks if the user is an admin
     * @param request the request
     * @return true if the user is an admin, false if not
     */
    public boolean isAdmin(HttpServletRequest request);

}
