package com.caissemagasin.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private Map<String, Integer> products;
    private Map<String, Double> productPrices;
    private double totalPrice;

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new HashMap<>();
        this.productPrices = new HashMap<>();
        this.totalPrice = 0.0;
    }

    public int getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return Math.round(totalPrice * 100.0) / 100.0; // Arrondi à 2 décimales
    }

    public void addProduct(Product product, int quantity) {
        String productName = product.getName();
        double productPrice = product.getPrice();

        if (products.containsKey(productName)) {
            products.put(productName, products.get(productName) + quantity);
        } else {
            products.put(productName, quantity);
            productPrices.put(productName, productPrice);
        }

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += quantity * productPrices.get(productName);
        }
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
    }

    public double getProductTotal(String productName) {
        return Math.round(products.get(productName) * productPrices.get(productName) * 100.0) / 100.0;
    }
}
