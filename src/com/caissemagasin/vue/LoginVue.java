package com.caissemagasin.vue;

import com.caissemagasin.controller.LoginController;

/**
 * The LoginVue class is responsible for managing the user interface for logging in to the
 * system. It provides a method to launch the login menu and interacts with the LoginController
 * to handle the actual login process.
 * 
 * This class implements the VueFunction interface, which defines the structure for view classes.
 */
public class LoginVue implements VueFunction {

    private final LoginController loginController;

    /**
     * Constructs a new LoginVue with the provided LoginController.
     * 
     * @param loginController The controller that handles the login logic.
     */
    public LoginVue(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Launches the login menu by displaying the title and initiating the login process
     * via the LoginController.
     */
    public void launchMenu() {
        printTitle("Connexion à la caisse du magasin");
        loginController.doLogin();
    }
}
