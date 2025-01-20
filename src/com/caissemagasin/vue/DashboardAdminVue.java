package com.caissemagasin.vue;

import com.caissemagasin.controller.AdminController;
import com.caissemagasin.controller.OrderController;
import com.caissemagasin.controller.ProductController;
import com.caissemagasin.model.User;

public class DashboardAdminVue implements VueFunction {
    private final AdminController adminController = new AdminController();
    private final ProductController productController = new ProductController();
    private final OrderController orderController = new OrderController();

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void printMenuAdmin() {
        while (true) {
            ConsoleUI.printTitle("MENU ADMINISTRATEUR");

            printMessage(ConsoleUI.BLUE + "[1] Créer un utilisateur" + ConsoleUI.RESET);
            printMessage(ConsoleUI.BLUE + "[2] Modifier un utilisateur" + ConsoleUI.RESET);
            printMessage(ConsoleUI.BLUE + "[3] Supprimer un utilisateur" + ConsoleUI.RESET);

            printMessage(ConsoleUI.CYAN + "[4] Ajouter un produit" + ConsoleUI.RESET);
            printMessage(ConsoleUI.CYAN + "[5] Modifier un produit" + ConsoleUI.RESET);
            printMessage(ConsoleUI.CYAN + "[6] Supprimer un produit" + ConsoleUI.RESET);

            printMessage(ConsoleUI.YELLOW + "[7] Initier une commande" + ConsoleUI.RESET);
            printMessage(ConsoleUI.YELLOW + "[8] Rechercher une commande" + ConsoleUI.RESET);

            printMessage(ConsoleUI.RED + "[9] Déconnexion" + ConsoleUI.RESET);

            ConsoleUI.printSeparator();
            String choix = scanInput(ConsoleUI.YELLOW + "Votre choix : " + ConsoleUI.RESET);

            switch (choix) {
                case "1":
                    adminController.createUser();
                    break;
                case "2":
                    adminController.updateUser();
                    break;
                case "3":
                    adminController.deleteUser(this);
                    break;
                case "4":
                    productController.createProduct();
                    break;
                case "5":
                    productController.updateProduct();
                    break;
                case "6":
                    productController.deleteProduct();
                    break;
                case "7":
                    orderController.initiateOrder();
                    break;
                case "8":
                    break;
                case "9":
                    ConsoleUI.successMessage("Déconnexion réussie !");
                    return;
                default:
                    ConsoleUI.errorMessage("Option non valide !");
            }
        }
    }
}
