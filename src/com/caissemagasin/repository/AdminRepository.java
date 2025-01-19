package com.caissemagasin.repository;

import com.caissemagasin.model.User;

import java.io.*;
import java.util.List;

public class AdminRepository {
    private static final String FILE_PATH = "data/users.csv";

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

    public void updateUser(String login, User updatedUser) {
        List<User> users = new LoginRepository().searchUsers();
        boolean found = false;

        for (User user : users) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                user.setName(updatedUser.getName());
                user.setSurname(updatedUser.getSurname());
                user.setAdmin(updatedUser.getAdmin());
                user.setActive(updatedUser.getActive());
                found = true;
                break;
            }
        }

        if (found) {
            saveAllUsers(users);
        }
    }

    public boolean deleteUser(String login) {
        List<User> users = new LoginRepository().searchUsers();
        boolean found = users.removeIf(user -> user.getLogin().equalsIgnoreCase(login));

        if (found) {
            return saveAllUsers(users);
        }
        return false;
    }
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
