package com.caissemagasin.vue;

import com.caissemagasin.controller.LoginController;

public class LoginVue implements VueFunction {
    private final LoginController loginController;

    public LoginVue(LoginController loginController) {
        this.loginController = loginController;
    }

    public void launchMenu() {
        printTitle("Connexion à la caisse du magasin");
        loginController.doLogin();
    }
}
