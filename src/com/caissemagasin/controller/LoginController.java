package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.service.LoginService;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.LoginVue;

public class LoginController {
    private final LoginService loginService = new LoginService();
    private final LoginVue loginVue = new LoginVue();
    private final DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();


    public void doLogin() {
        String login = loginVue.scanInput("Login de l'utilisateur : ");
        String password = loginVue.scanInput("Mot de passe : ");

        User user = loginService.authenticationUser(login, password);

        if (user != null) {
            loginVue.printMessage("Connexion réussie !");
            redirectToMenu(user);
        } else {
            loginVue.printMessage("Nom d'utilisateur ou mot de passe incorrect");
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
