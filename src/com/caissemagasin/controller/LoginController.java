package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.service.LoginService;
import com.caissemagasin.vue.ConsoleUI;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.LoginVue;

public class LoginController {
    private final LoginService loginService = new LoginService();
    private final LoginVue loginVue = new LoginVue();
    private final DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
    private final AdminService adminService = new AdminService();


    public void doLogin() {
        String login = loginVue.scanInput("Login de l'utilisateur : ");

        User user=loginService.isActive(login);
        if (user!=null && !user.getActive()) {
            loginVue.printMessage("C'est votre première connexion tapez votre mot de passe");
            String password = loginVue.scanInput("Mot de passe : ");
            String passwordConfirm = loginVue.scanInput("Confirmation de Mot de passe : ");
            if (password.equals(passwordConfirm)) {
                user.setPassword(password);
                user.setActive(true);
                adminService.updateUser(login, user);
                ConsoleUI.successMessage("Connexion réussie !");
                redirectToMenu(user);
                return;
            }else {
                ConsoleUI.errorMessage("Les deux mot de passe ne matchent pas");
                System.exit(0);
            }
        }
        String password = loginVue.scanInput("Mot de passe : ");

        if (loginService.authenticationUser(user, password)) {
            ConsoleUI.successMessage("Connexion réussie !");
            redirectToMenu(user);
        } else {
            ConsoleUI.errorMessage("Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    private void redirectToMenu(User user) {
        if (user.getAdmin()) {
            dashboardAdminVue.setUser(user);
            dashboardAdminVue.printMenuAdmin();
        } else {
            loginVue.printMessage("Utilisateur standard, accès limité.");
        }
    }
}
