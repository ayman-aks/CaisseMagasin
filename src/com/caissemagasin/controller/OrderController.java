package com.caissemagasin.controller;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.model.User;
import com.caissemagasin.service.OrderService;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.DashboardUserVue;
import com.caissemagasin.vue.OrderVue;

import java.util.List;
import java.util.Map;

import static com.caissemagasin.vue.ConsoleUI.*;

public class OrderController {
    private OrderService orderService;
    private OrderVue orderVue;
    private DashboardAdminVue dashboardAdminVue;
    private DashboardUserVue dashboardUserVue;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.orderVue = null;
        this.dashboardAdminVue = null;
        this.dashboardUserVue = null;
    }

    public void initiateOrder(User user) {
        Order order = orderService.createNewOrder();
        orderVue.printTitle("NOUVELLE COMMANDE");

        while (true) {
            String input = orderVue.scanInput("\n" + BLUE + "🔍 Recherchez un produit (nom ou ID) ou tapez 'fin' : " + RESET);

            if (input.equalsIgnoreCase("fin")) {
                break;
            } else if (input.matches("\\d+")) {
                int id = Integer.parseInt(input);
                Product product = orderService.getProductById(id);
                if (product != null) {
                    int quantity = askForQuantity();
                    order.addProduct(product, quantity);
                    orderVue.successMessage(quantity + " x " + product.getName() + " ajouté !");
                } else {
                    orderVue.errorMessage("Produit introuvable.");
                }
            } else {
                int page = 0;
                int pageSize = 5;
                Map<String, ?> responseSearchProduct;
                while (true) {
                    responseSearchProduct = orderService.searchProducts(input, page, pageSize);
                    List<Product> products = (List<Product>) responseSearchProduct.get("products");

                    if (products.isEmpty()) {
                        orderVue.errorMessage("Aucun produit trouvé.");
                        break;
                    }

                    orderVue.printTitle("Produits trouvés");
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        System.out.printf(YELLOW + "[%d] %s - %.2f€%n" + RESET, i + 1, p.getName(), p.getPrice());
                    }
                    orderVue.printMessage(CYAN + "Page " + (page + 1) + "/" + (Integer.parseInt(responseSearchProduct.get("nbrProducts").toString()) / pageSize + 1));

                    String choix = orderVue.scanInput(CYAN + "Choisissez un produit (1-5) ou 'next' ou 'back': " + RESET);
                    if (choix.equalsIgnoreCase("next")) {
                        if (page != Integer.parseInt(responseSearchProduct.get("nbrProducts").toString()) / pageSize) {
                            page++;
                        }
                    } else if (choix.equalsIgnoreCase("back")) {
                        page--;
                        if (page < 0) {
                            page = 0;
                        }
                    } else if (choix.matches("[1-5]")) {
                        int index = Integer.parseInt(choix) - 1;
                        if (index >= 0 && index < products.size()) {
                            Product selectedProduct = products.get(index);
                            int quantity = askForQuantity();
                            order.addProduct(selectedProduct, quantity);
                            orderVue.successMessage(quantity + " x " + selectedProduct.getName() + " ajouté !");
                        } else {
                            orderVue.errorMessage("Choix invalide.");
                        }
                        break;
                    } else {
                        orderVue.errorMessage("Commande annulée.");
                        break;
                    }
                }
            }
        }

        if (orderService.finalizeOrder(order, user)) {
            orderVue.successMessage("Commande enregistrée avec succès !");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            orderVue.printMessage(orderService.searchOrder(String.valueOf(order.getOrderId())));
            orderVue.scanInput("\n" + BLUE + "Tapez entrer pour revenir vers le menu du dashboard :" + RESET);
        } else {
            orderVue.errorMessage("Erreur lors de l'enregistrement.");
        }

        if (user.getAdmin()) {
            dashboardAdminVue.printMenuAdmin();
        }else {
            dashboardUserVue.printMenuUser();
        }
    }

    private int askForQuantity() {
        String quantityInput = orderVue.scanInput(CYAN + "Entrez la quantité (Entrée pour 1) : " + RESET);
        return quantityInput.isEmpty() ? 1 : Integer.parseInt(quantityInput);
    }

    public void searchOrder(User user) {
        orderVue.printTitle("Rechercher une commande");
        String input = orderVue.scanInput("\n" + BLUE + "🔍 Recherchez une commande par son ID :" + RESET);
        String orderToPrint = orderService.searchOrder(input);
        if (orderToPrint == null) {
            orderVue.errorMessage("Commande non retrouvé");
        } else {
            orderVue.printMessage(orderToPrint);
        }
        orderVue.scanInput("\n" + BLUE + "Tapez entrer pour revenir vers le menu du dashboard :" + RESET);
        if (user.getAdmin()) {
            dashboardAdminVue.printMenuAdmin();
        }else{
            dashboardUserVue.printMenuUser();
        }
    }

    public void setOrderVue(OrderVue orderVue) {
        this.orderVue = orderVue;
    }

    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }

    public void setDashboardUserVue(DashboardUserVue dashboardUserVue) {
        this.dashboardUserVue = dashboardUserVue;
    }
}
