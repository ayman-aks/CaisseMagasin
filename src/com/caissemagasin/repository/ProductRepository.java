package com.caissemagasin.repository;

import com.caissemagasin.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final String FILE_PATH = "data/produits.csv";

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
            System.out.println("Erreur de lecture du fichier produits : " + e.getMessage());
        }
        return products;
    }

    public boolean save(Product product) {
        List<Product> products = searchProducts();

        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                System.out.println("Erreur : Un produit avec ce nom existe déjà !");
                return false;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String productLine = product.getId() + ";" + product.getName() + ";" + product.getPrice();
            bw.write(productLine);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier produits : " + e.getMessage());
            return false;
        }
    }

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

    public boolean deleteProduct(String name) {
        List<Product> products = searchProducts();
        boolean found = products.removeIf(product -> product.getName().equalsIgnoreCase(name));

        if (found) {
            return saveAllProducts(products);
        }
        return false;
    }

    private boolean saveAllProducts(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                String productLine = product.getId() + ";" + product.getName() + ";" + product.getPrice();
                bw.write(productLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Erreur de mise à jour du fichier produits : " + e.getMessage());
            return false;
        }
    }

    public Product findByName(String name) {
        for (Product product : searchProducts()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
