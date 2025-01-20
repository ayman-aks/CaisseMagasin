package com.caissemagasin.controller;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.service.OrderService;
import com.caissemagasin.vue.ConsoleUI;
import com.caissemagasin.vue.DashboardAdminVue;

import java.util.List;

public class OrderController {
    private final OrderService orderService = new OrderService();

    public void initiateOrder() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        Order order = orderService.createNewOrder();
        ConsoleUI.printTitle("NOUVELLE COMMANDE");

        while (true) {
            String input = dashboardAdminVue.scanInput("\n" + ConsoleUI.BLUE + "🔍 Recherchez un produit (nom ou ID) ou tapez 'fin' : " + ConsoleUI.RESET);

            if (input.equalsIgnoreCase("fin")) {
                break;
            } else if (input.matches("\\d+")) {
                int id = Integer.parseInt(input);
                Product product = orderService.getProductById(id);
                if (product != null) {
                    int quantity = askForQuantity(dashboardAdminVue);
                    order.addProduct(product, quantity);
                    ConsoleUI.successMessage(quantity + " x " + product.getName() + " ajouté !");
                } else {
                    ConsoleUI.errorMessage("Produit introuvable.");
                }
            } else {
                int page = 0;
                int pageSize = 5;
                while (true) {
                    List<Product> products = orderService.searchProducts(input, page, pageSize);

                    if (products.isEmpty()) {
                        ConsoleUI.errorMessage("Aucun produit trouvé.");
                        break;
                    }

                    ConsoleUI.printSeparator();
                    ConsoleUI.printTitle("Produits trouvés");
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        System.out.printf(ConsoleUI.YELLOW + "[%d] %s - %.2f€%n" + ConsoleUI.RESET, i + 1, p.getName(), p.getPrice());
                    }

                    String choix = dashboardAdminVue.scanInput(ConsoleUI.CYAN + "Choisissez un produit (1-5) ou 'next' : " + ConsoleUI.RESET);
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
                            ConsoleUI.successMessage(quantity + " x " + selectedProduct.getName() + " ajouté !");
                        } else {
                            ConsoleUI.errorMessage("Choix invalide.");
                        }
                        break;
                    } else {
                        ConsoleUI.errorMessage("Commande annulée.");
                        break;
                    }
                }
            }
        }

        if (orderService.finalizeOrder(order)) {
            ConsoleUI.successMessage("Commande enregistrée avec succès !");
        } else {
            ConsoleUI.errorMessage("Erreur lors de l'enregistrement.");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    private int askForQuantity(DashboardAdminVue dashboardAdminVue) {
        String quantityInput = dashboardAdminVue.scanInput(ConsoleUI.CYAN + "Entrez la quantité (Entrée pour 1) : " + ConsoleUI.RESET);
        return quantityInput.isEmpty() ? 1 : Integer.parseInt(quantityInput);
    }
}
