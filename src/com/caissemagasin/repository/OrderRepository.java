package com.caissemagasin.repository;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.model.User;

import java.io.*;
import java.util.Map;

public class OrderRepository {
    private static final String ORDER_DIRECTORY = "data/orders/";

    public int getNextOrderId() {
        File dir = new File(ORDER_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.listFiles() == null ? 1 : dir.listFiles().length + 1;
    }

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

    public String searchOrderById(String id) {
        String order= "";
        String filePath = ORDER_DIRECTORY + "order_" + id + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                order+=line+"\n";
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
