package com.caissemagasin.service;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;

public class LoginService {
    private final LoginRepository loginRepository = new LoginRepository();

    public User authenticationUser(String login, String password) {
        User user = loginRepository.findByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
