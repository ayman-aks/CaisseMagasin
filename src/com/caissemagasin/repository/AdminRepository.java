package com.caissemagasin.repository;

import com.caissemagasin.model.User;

import java.io.*;
import java.util.List;

/**
 * Repository class for managing users with admin rights.
 * This class provides functionality to save, update, and delete users in a CSV file.
 */
public class AdminRepository {
    private static final String FILE_PATH = "data/users.csv";

    /**
     * Saves a new user to the CSV file.
     * It first checks if a user with the same login already exists. If not, the user is added to the file.
     * 
     * @param user the user to be saved
     * @return true if the user is successfully saved, false otherwise
     */
    public boolean save(User user) {
        List<User> users = new LoginRepository().searchUsers();

        for (User u : users) {
            if (u.getLogin().equalsIgnoreCase(user.getLogin())) {
                System.out.println("Erreur : Un utilisateur avec ce login existe déjà !");
                return false;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String userLine = user.getId() + ";" + user.getName() + ";" + user.getSurname() + ";" +
                    user.getLogin() + ";" + user.getPassword() + ";" + user.getAdmin() + ";" + user.getActive();
            bw.write(userLine);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier CSV : " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user based on their login.
     * If a user is found, their information is updated with the new data provided.
     * 
     * @param login the login of the user to update
     * @param updatedUser the user object containing the updated data
     */
    public void updateUser(String login, User updatedUser) {
        List<User> users = new LoginRepository().searchUsers();
        boolean found = false;

        for (User user : users) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                user.setName(updatedUser.getName());
                user.setSurname(updatedUser.getSurname());
                user.setAdmin(updatedUser.getAdmin());
                user.setActive(updatedUser.getActive());
                if (updatedUser.getPassword() != null) {
                    user.setPassword(updatedUser.getPassword());
                }
                found = true;
                break;
            }
        }

        if (found) {
            saveAllUsers(users);
        }
    }

    /**
     * Deletes a user from the system based on their login.
     * If a user is found, they are removed from the list and the changes are saved to the CSV file.
     * 
     * @param login the login of the user to delete
     * @return true if the user is successfully deleted, false otherwise
     */
    public boolean deleteUser(String login) {
        List<User> users = new LoginRepository().searchUsers();
        boolean found = users.removeIf(user -> user.getLogin().equalsIgnoreCase(login));

        if (found) {
            return saveAllUsers(users);
        }
        return false;
    }

    /**
     * Saves all users to the CSV file after updating or deleting a user.
     * 
     * @param users the list of users to save to the file
     * @return true if the users are successfully saved, false otherwise
     */
    private boolean saveAllUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                String userLine = user.getId() + ";" + user.getName() + ";" + user.getSurname() + ";" +
                        user.getLogin() + ";" + user.getPassword() + ";" + user.getAdmin() + ";" + user.getActive();
                bw.write(userLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Erreur de mise à jour du fichier CSV : " + e.getMessage());
            return false;
        }
    }
}
