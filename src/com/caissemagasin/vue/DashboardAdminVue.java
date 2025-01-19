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
            printMessage("\n==== MENU ADMIN ====");
            printMessage("1. Créer un utilisateur");
            printMessage("2. Modifier un utilisateur");
            printMessage("3. Supprimer un utilisateur");
            printMessage("4. Ajouter un produit");
            printMessage("5. Modifier un produit");
            printMessage("6. Supprimer un produit");
            printMessage("7. Initier une commande");
            printMessage("9. Déconnexion");

            String choix = scanInput("Choix : ");

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
                case "9":
                    printMessage("Déconnexion...");
                    return;
                default:
                    printMessage("Option non valide !");
            }
        }
    }
}
