package com.caissemagasin.repository;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.model.User;

import java.io.*;
import java.util.Map;

/**
 * Repository class for managing orders.
 * This class provides functionality to save orders to files, retrieve the next order ID, and search for orders by ID.
 */
public class OrderRepository {
    private static final String ORDER_DIRECTORY = "data/orders/";

    /**
     * Retrieves the next available order ID.
     * It checks the number of existing orders in the directory and returns the next available ID.
     * If no orders exist, it returns 1.
     * 
     * @return the next order ID
     */
    public int getNextOrderId() {
        File dir = new File(ORDER_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.listFiles() == null ? 1 : dir.listFiles().length + 1;
    }

    /**
     * Saves an order to a text file.
     * The order details, including product names, quantities, and total price, are written to a text file.
     * The file is named based on the order ID and stored in the orders directory.
     * The user who processed the order is also recorded in the file.
     * 
     * @param order the order to be saved
     * @param user the user who processed the order
     * @return true if the order is successfully saved, false otherwise
     */
    public boolean saveOrder(Order order, User user) {
        String filePath = ORDER_DIRECTORY + "order_" + order.getOrderId() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("===== TICKET DE CAISSE =====\n");
            bw.write("Commande ID : " + order.getOrderId() + "\n");
            bw.write("Produits :\n");

            for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                double totalProductPrice = order.getProductTotal(productName);
                bw.write(String.format("- %s x%d : %.2f€\n", productName, quantity, totalProductPrice));
            }

            bw.write(String.format("\nTotal : %.2f€\n", order.getTotalPrice()));
            bw.write(String.format("\nAdministrateur de vente : " + user.getLogin() + "\n"));
            bw.write("============================");
            return true;
        } catch (IOException e) {
            System.out.println("Erreur d'écriture du fichier commande : " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches for an order by its ID.
     * It reads the corresponding order file and returns the contents as a string.
     * 
     * @param id the ID of the order to search for
     * @return the order details as a string, or null if the order is not found
     */
    public String searchOrderById(String id) {
        String order = "";
        String filePath = ORDER_DIRECTORY + "order_" + id + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                order += line + "\n";
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier produits : " + e.getMessage());
        }
        if (order.isBlank()) {
            return null;
        }
        return order.toString();
    }
}
