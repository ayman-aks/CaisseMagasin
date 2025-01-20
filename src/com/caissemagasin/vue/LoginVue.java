package com.caissemagasin.vue;
import com.caissemagasin.controller.LoginController;

public class LoginVue implements VueFunction{

    public void launchMenu() {
        LoginController loginController = new LoginController();
        ConsoleUI.printTitle("Connexion à la caisse du magasin");
        loginController.doLogin();
    }
}
