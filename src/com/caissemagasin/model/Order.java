package com.caissemagasin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Order in the system.
 * The Order contains the order ID, a list of products with quantities, the prices of products, 
 * and the total price of the order. The order can be modified by adding products to it.
 */
public class Order {
    private int orderId;
    private Map<String, Integer> products;
    private Map<String, Double> productPrices;
    private double totalPrice;

    /**
     * Constructs an Order with the specified order ID.
     * Initializes the products and productPrices maps, and sets the totalPrice to 0.
     * 
     * @param orderId the unique identifier for the order
     */
    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new HashMap<>();
        this.productPrices = new HashMap<>();
        this.totalPrice = 0.0;
    }

    /**
     * Gets the unique identifier of the order.
     * 
     * @return the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Gets the map of products in the order, where the key is the product name 
     * and the value is the quantity of that product in the order.
     * 
     * @return a map of products and their quantities
     */
    public Map<String, Integer> getProducts() {
        return products;
    }

    /**
     * Gets the total price of the order, rounded to 2 decimal places.
     * 
     * @return the total price of the order
     */
    public double getTotalPrice() {
        return Math.round(totalPrice * 100.0) / 100.0; // Rounded to 2 decimal places
    }

    /**
     * Adds a product to the order with the specified quantity.
     * If the product already exists in the order, its quantity will be updated.
     * If the product is new, it will be added with its price.
     * 
     * @param product the product to be added to the order
     * @param quantity the quantity of the product to be added
     */
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

    /**
     * Updates the total price of the order based on the products and their quantities.
     * The total price is calculated as the sum of the product prices multiplied by their quantities.
     */
    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += quantity * productPrices.get(productName);
        }
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
    }

    /**
     * Gets the total price of a specific product in the order, 
     * which is calculated as the quantity of the product multiplied by its price.
     * The result is rounded to 2 decimal places.
     * 
     * @param productName the name of the product
     * @return the total price of the product in the order
     */
    public double getProductTotal(String productName) {
        return Math.round(products.get(productName) * productPrices.get(productName) * 100.0) / 100.0;
    }
}
