package com.caissemagasin.model;

/**
 * Represents a Product in the shopping system.
 * A product has an ID, a name, and a price.
 */
public class Product {
    private int id;
    private String name;
    private double price;

    /**
     * Constructs a Product with the specified ID, name, and price.
     * 
     * @param id the unique identifier for the product
     * @param name the name of the product
     * @param price the price of the product
     */
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the unique identifier of the product.
     * 
     * @return the product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     * 
     * @param id the product ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     * 
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * 
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product.
     * 
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * 
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
