package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.service.LoginService;
import com.caissemagasin.vue.ConsoleUI;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.LoginVue;

public class LoginController {
    private  LoginService loginService;
    private  LoginVue loginVue;
    private  DashboardAdminVue dashboardAdminVue;
    private  AdminService adminService;

    public LoginController(LoginService loginService, AdminService adminService) {
        this.loginService = loginService;
        this.adminService = adminService;
        this.loginVue = null;
        this.dashboardAdminVue = null;
    }

    public void doLogin() {
        String login = loginVue.scanInput("Login de l'utilisateur : ");

        User user = loginService.isActive(login);
        if (user != null && !user.getActive()) {
            loginVue.printMessage("C'est votre première connexion, tapez votre mot de passe.");
            String password = loginVue.scanInput("Mot de passe : ");
            String passwordConfirm = loginVue.scanInput("Confirmation du mot de passe : ");
            if (password.equals(passwordConfirm)) {
                user.setPassword(password);
                user.setActive(true);
                adminService.updateUser(login, user);
                loginVue.successMessage("Connexion réussie !");
                redirectToMenu(user);
                return;
            } else {
                loginVue.errorMessage("Les deux mots de passe ne correspondent pas.");
                System.exit(0);
            }
        }

        String password = loginVue.scanInput("Mot de passe : ");

        if (loginService.authenticationUser(user, password)) {
            loginVue.successMessage("Connexion réussie !");
            redirectToMenu(user);
        } else {
            loginVue.errorMessage("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private void redirectToMenu(User user) {
        if (user.getAdmin()) {
            dashboardAdminVue.setUser(user);
            dashboardAdminVue.printMenuAdmin();
        } else {
            //A changer après implémentation du user
            loginVue.printMessage("Utilisateur standard, accès limité.");
        }
    }

    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }

    public void setLoginVue(LoginVue loginVue) {
        this.loginVue = loginVue;
    }
}
