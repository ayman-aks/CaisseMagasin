package com.caissemagasin.repository;

import com.caissemagasin.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing product data.
 * This class provides functionality to search, save, update, delete, and retrieve products from a CSV file.
 */
public class ProductRepository {
    private static final String FILE_PATH = "data/produits.csv";

    /**
     * Searches for all products in the CSV file and returns them as a list of Product objects.
     * Each line in the file represents a product with its details, and the method parses the file 
     * to create Product objects for each line.
     * 
     * @return a list of products found in the CSV file
     */
    public List<Product> searchProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    products.add(new Product(id, name, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the product file: " + e.getMessage());
        }
        return products;
    }

    /**
     * Saves a new product to the CSV file.
     * The method first checks if a product with the same name already exists. If not, the new product is appended to the file.
     * 
     * @param product the product to be saved
     * @return true if the product is successfully saved, false otherwise
     */
    public boolean save(Product product) {
        List<Product> products = searchProducts();

        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                System.out.println("Error: A product with this name already exists!");
                return false;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String productLine = product.getId() + ";" + product.getName() + ";" + product.getPrice();
            bw.write(productLine);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to the product file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates a product's details by name.
     * If the product is found, its name and price are updated with the new values. 
     * The list of products is then saved back to the file.
     * 
     * @param name the name of the product to update
     * @param updatedProduct the product with the updated details
     */
    public void updateProduct(String name, Product updatedProduct) {
        List<Product> products = searchProducts();
        boolean found = false;

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                found = true;
                break;
            }
        }

        if (found) {
            saveAllProducts(products);
        }
    }

    /**
     * Deletes a product by its name.
     * If the product is found, it is removed from the list, and the updated list is saved to the file.
     * 
     * @param name the name of the product to delete
     * @return true if the product is successfully deleted, false otherwise
     */
    public boolean deleteProduct(String name) {
        List<Product> products = searchProducts();
        boolean found = products.removeIf(product -> product.getName().equalsIgnoreCase(name));

        if (found) {
            return saveAllProducts(products);
        }
        return false;
    }

    /**
     * Saves all products to the CSV file.
     * This method writes the current list of products to the file, overwriting the previous data.
     * 
     * @param products the list of products to be saved
     * @return true if the products are successfully saved, false otherwise
     */
    private boolean saveAllProducts(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                String productLine = product.getId() + ";" + product.getName() + ";" + product.getPrice();
                bw.write(productLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error updating the product file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds a product by its name.
     * It searches through the list of products and returns the first product that matches the given name.
     * 
     * @param name the name of the product to search for
     * @return the product with the specified name, or null if no product is found
     */
    public Product findByName(String name) {
        for (Product product : searchProducts()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
