package com.caissemagasin;

import com.caissemagasin.configuration.AppContext;
import com.caissemagasin.vue.LoginVue;

/**
 * The Main class is the entry point of the application.
 * It is responsible for initializing the application context, 
 * setting up the login view, and launching the login menu.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     * It initializes the application context and displays the login view.
     * 
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Initialize the application context and get the LoginVue instance.
        AppContext appContext = AppContext.getInstance();
        LoginVue loginVue = appContext.getLoginVue();

        // Launch the login menu.
        loginVue.launchMenu();
    }
}
