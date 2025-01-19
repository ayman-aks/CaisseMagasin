package com.caissemagasin.controller;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.service.OrderService;
import com.caissemagasin.vue.DashboardAdminVue;

import java.util.List;

public class OrderController {
    private final OrderService orderService = new OrderService();

    public void initiateOrder() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        Order order = orderService.createNewOrder();
        dashboardAdminVue.printMessage("\n=== Nouvelle commande initiée ===\n");

        while (true) {
            String input = dashboardAdminVue.scanInput("\nEntrez un ID produit ou un nom (ou tapez 'fin' pour terminer) : ");

            if (input.equalsIgnoreCase("fin")) {
                break;
            } else if (input.matches("\\d+")) {
                int id = Integer.parseInt(input);
                Product product = orderService.getProductById(id);
                if (product != null) {
                    int quantity = askForQuantity(dashboardAdminVue);
                    order.addProduct(product, quantity);
                    dashboardAdminVue.printMessage(quantity + " x " + product.getName() + " ajouté/mis à jour dans la commande.");
                } else {
                    dashboardAdminVue.printMessage("Produit introuvable.");
                }
            } else {
                int page = 0;
                int pageSize = 5;
                while (true) {
                    List<Product> products = orderService.searchProducts(input, page, pageSize);

                    if (products.isEmpty()) {
                        dashboardAdminVue.printMessage("Aucun produit trouvé.");
                        break;
                    }

                    dashboardAdminVue.printMessage("\n=== Résultats de la recherche ===");
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        dashboardAdminVue.printMessage((i + 1) + ". " + p.getName() + " - " + p.getPrice() + "€");
                    }

                    String choix = dashboardAdminVue.scanInput("Choisissez un produit (1-5) ou 'next' pour voir plus : ");
                    if (choix.equalsIgnoreCase("next")) {
                        page++;
                        List<Product> nextPageProducts = orderService.searchProducts(input, page, pageSize);
                        if (nextPageProducts.isEmpty()) {
                            page = 0;
                        }
                    } else if (choix.matches("[1-5]")) {
                        int index = Integer.parseInt(choix) - 1;
                        if (index >= 0 && index < products.size()) {
                            Product selectedProduct = products.get(index);
                            int quantity = askForQuantity(dashboardAdminVue);
                            order.addProduct(selectedProduct, quantity);
                            dashboardAdminVue.printMessage(quantity + " x " + selectedProduct.getName() + " ajouté/mis à jour dans la commande.");
                        } else {
                            dashboardAdminVue.printMessage("Choix invalide.");
                        }
                        break;
                    } else {
                        dashboardAdminVue.printMessage("Commande annulée.");
                        break;
                    }
                }
            }
        }

        if (orderService.finalizeOrder(order)) {
            dashboardAdminVue.printMessage("\n=== Commande enregistrée avec succès ===");
        } else {
            dashboardAdminVue.printMessage("\n=== Erreur lors de l'enregistrement de la commande ===");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    private int askForQuantity(DashboardAdminVue dashboardAdminVue) {
        String quantityInput = dashboardAdminVue.scanInput("Entrez la quantité (appuyez sur Entrée pour 1) : ");
        return quantityInput.isEmpty() ? 1 : Integer.parseInt(quantityInput);
    }
}
