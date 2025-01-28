package com.caissemagasin.controller;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.model.User;
import com.caissemagasin.service.OrderService;
import com.caissemagasin.vue.OrderVue;

import java.util.List;
import java.util.Map;

import static com.caissemagasin.vue.ConsoleUI.*;
/**
 * Controller class to handle operations related to orders within the application.
 * It bridges the interaction between models, views, and services.
 */
public class OrderController {
    /**
     * Service to handle business logic related to orders.
     */
    private OrderService orderService;

    /**
     * View for displaying order-related information to the user.
     */
    private OrderVue orderVue;

    /**
     * Constructs an OrderController with a given order service.
     *
     * @param orderService Service to manage orders and related operations.
     */
    public OrderController(OrderService orderService,OrderVue orderVue) {
        this.orderService = orderService;
        this.orderVue = orderVue;
    }

    /**
     * Starts the order creation process for a given user.
     * Allows the user to search for products, add them to an order, and finalize the order.
     *
     * @param user The user initiating the order (can be admin or regular user).
     */
    @SuppressWarnings("unchecked")
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
    }

    /**
     * Prompts the user to input a quantity for a selected product.
     * Defaults to 1 if no input is provided.
     *
     * @return The quantity specified by the user, or 1 if left empty.
     */
    private int askForQuantity() {
        String quantityInput = orderVue.scanInput(CYAN + "Entrez la quantité (Entrée pour 1) : " + RESET);
        return quantityInput.isEmpty() ? 1 : Integer.parseInt(quantityInput);
    }

    /**
     * Allows the user to search for an existing order by its ID.
     * Displays the details of the order if found.
     */
    public void searchOrder() {
        orderVue.printTitle("Rechercher une commande");
        String input = orderVue.scanInput("\n" + BLUE + "🔍 Recherchez une commande par son ID :" + RESET);
        String orderToPrint = orderService.searchOrder(input);
        if (orderToPrint == null) {
            orderVue.errorMessage("Commande non retrouvé");
        } else {
            orderVue.printMessage(orderToPrint);
        }
        orderVue.scanInput("\n" + BLUE + "Tapez entrer pour revenir vers le menu du dashboard :" + RESET);
    }

}