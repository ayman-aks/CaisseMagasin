package com.caissemagasin.repository;

import com.caissemagasin.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginRepository {
    private static final String FILE_PATH = "data/users.csv";

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
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return users;
    }

    public User findByLogin(String login) {
        for (User user : searchUsers()) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                return user;
            }
        }
        return null;
    }
}
