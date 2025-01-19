package com.caissemagasin.vue;
import com.caissemagasin.controller.LoginController;
import java.util.Scanner;

public class LoginVue implements VueFunction{
    private static final Scanner scanner = new Scanner(System.in);

    public void launchMenu() {
        LoginController loginController = new LoginController();
        printMessage("=== Connexion à la caisse du magasin ===");
        loginController.doLogin();
    }
}
