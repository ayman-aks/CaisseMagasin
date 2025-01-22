package com.caissemagasin.service;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;

/**
 * Service class for handling user login and authentication.
 * Provides methods to authenticate users and check if a user is active.
 */
public class LoginService {
    private final LoginRepository loginRepository;

    /**
     * Constructs a LoginService instance with the specified login repository.
     *
     * @param loginRepository the repository responsible for user login-related actions
     */
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * Authenticates a user by comparing the provided password with the user's stored password.
     * 
     * @param user the user object containing login credentials
     * @param password the password to be authenticated
     * @return true if the password matches the user's stored password, false otherwise
     */
    public boolean authenticationUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Checks if a user is active by searching for the user in the login repository.
     * 
     * @param login the login of the user to check
     * @return the user object if found and active, null otherwise
     */
    public User isActive(String login) {
        return loginRepository.findByLogin(login);
    }
}
