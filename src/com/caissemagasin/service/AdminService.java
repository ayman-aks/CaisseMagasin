package com.caissemagasin.service;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.AdminRepository;
import com.caissemagasin.repository.LoginRepository;

import java.util.List;

public class AdminService {
    private final LoginRepository loginRepository = new LoginRepository();
    private final AdminRepository adminRepository = new AdminRepository();

    private int getNextUserId() {
        List<User> users = loginRepository.searchUsers();
        return users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
    }

    public boolean saveUser(User user) {
        user.setId(getNextUserId());
        return adminRepository.save(user);
    }

    public void updateUser(String login, User updatedUser) {
        adminRepository.updateUser(login, updatedUser);
    }

    public boolean deleteUser(String login) {
        return adminRepository.deleteUser(login);
    }
}
