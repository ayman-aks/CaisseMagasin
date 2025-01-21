package com.caissemagasin.service;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;

public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticationUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }

    public User isActive(String login) {
        return loginRepository.findByLogin(login);
    }
}
