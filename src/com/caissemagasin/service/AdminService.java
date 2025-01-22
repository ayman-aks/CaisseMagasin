package com.caissemagasin.service;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.AdminRepository;
import com.caissemagasin.repository.LoginRepository;

import java.util.List;

/**
 * Service class for managing administrative tasks related to users.
 * This class provides functionality to save, update, and delete users by interacting with the respective repositories.
 */
public class AdminService {
    private final LoginRepository loginRepository;
    private final AdminRepository adminRepository;

    /**
     * Constructs an AdminService instance with the specified repositories.
     *
     * @param loginRepository the repository responsible for login-related user actions
     * @param adminRepository the repository responsible for administrative actions on users
     */
    public AdminService(LoginRepository loginRepository, AdminRepository adminRepository) {
        this.loginRepository = loginRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Gets the next available user ID.
     * This method checks the list of existing users and returns the ID for the next user to be created.
     *
     * @return the next available user ID
     */
    private int getNextUserId() {
        List<User> users = loginRepository.searchUsers();
        return users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
    }

    /**
     * Saves a new user.
     * The user is assigned a new ID and saved using the admin repository.
     * 
     * @param user the user to be saved
     * @return true if the user is successfully saved, false otherwise
     */
    public boolean saveUser(User user) {
        user.setId(getNextUserId());
        return adminRepository.save(user);
    }

    /**
     * Updates an existing user's details by their login.
     * The user's details are updated with the information from the updatedUser object.
     * 
     * @param login the login of the user to update
     * @param updatedUser the user object containing updated information
     */
    public void updateUser(String login, User updatedUser) {
        adminRepository.updateUser(login, updatedUser);
    }

    /**
     * Deletes a user by their login.
     * The user is removed from the repository if found.
     *
     * @param login the login of the user to delete
     * @return true if the user is successfully deleted, false otherwise
     */
    public boolean deleteUser(String login) {
        return adminRepository.deleteUser(login);
    }
}
