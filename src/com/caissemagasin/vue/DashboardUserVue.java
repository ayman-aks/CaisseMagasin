package com.caissemagasin.vue;

import com.caissemagasin.controller.OrderController;

public class DashboardUserVue extends DashboardVue implements VueFunction {
    private final OrderController orderController;

    public DashboardUserVue(OrderController orderController) {
        this.orderController = orderController;
    }

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
                    orderController.searchOrder(user);
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
