package com.caissemagasin.vue;

import com.caissemagasin.controller.OrderController;

/**
 * The DashboardUserVue class represents the user dashboard interface in the console application.
 * It handles displaying the menu options for regular users and invoking the corresponding actions
 * through the provided OrderController.
 * 
 * This class extends {@link DashboardVue} and implements {@link VueFunction}.
 */
public class DashboardUserVue extends DashboardVue implements VueFunction {

    private final OrderController orderController;

    /**
     * Constructs a new instance of DashboardUserVue with the given OrderController.
     * 
     * @param orderController The controller for managing orders.
     */
    public DashboardUserVue(OrderController orderController) {
        this.orderController = orderController;
    }

    /**
     * Displays the user menu and allows the user to select various actions such as
     * initiating or searching for orders.
     */
    public void printMenuUser() {
        while (true) {
            printTitle("MENU User");
            printMessage(ConsoleUI.YELLOW + "[1] Initier une commande" + ConsoleUI.RESET);
            printMessage(ConsoleUI.YELLOW + "[2] Rechercher une commande" + ConsoleUI.RESET);

            printMessage(ConsoleUI.RED + "[3] Déconnexion" + ConsoleUI.RESET);

            printSeparator();
            String choix = scanInput(ConsoleUI.YELLOW + "Votre choix : " + ConsoleUI.RESET);

            switch (choix) {
                case "1":
                    orderController.initiateOrder(user);
                    break;
                case "2":
                    orderController.searchOrder();
                    break;
                case "3":
                    successMessage("Déconnexion réussie !");
                    System.exit(1);
                default:
                    errorMessage("Option non valide !");
            }
        }
    }
}
