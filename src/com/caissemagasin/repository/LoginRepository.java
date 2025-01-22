package com.caissemagasin.repository;

import com.caissemagasin.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing login operations and user retrieval.
 * This class provides functionality to search users from a CSV file and find a user by their login.
 */
public class LoginRepository {
    private static final String FILE_PATH = "data/users.csv";

    /**
     * Searches for all users in the CSV file and returns them as a list of User objects.
     * Each line in the file represents a user with their details, and the method parses the file 
     * to create User objects for each line.
     * 
     * @return a list of users found in the CSV file
     */
    public List<User> searchUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String surname = parts[2];
                    String login = parts[3];
                    String password = parts[4];
                    Boolean isAdmin = Boolean.parseBoolean(parts[5]);
                    Boolean isActive = Boolean.parseBoolean(parts[6]);
                    users.add(new User(id, name, surname, login, password, isAdmin, isActive));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return users;
    }

    /**
     * Finds a user by their login in the CSV file.
     * It searches through the list of users and returns the user that matches the provided login.
     * 
     * @param login the login of the user to search for
     * @return the user with the specified login, or null if no user is found
     */
    public User findByLogin(String login) {
        for (User user : searchUsers()) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                return user;
            }
        }
        return null;
    }
}
