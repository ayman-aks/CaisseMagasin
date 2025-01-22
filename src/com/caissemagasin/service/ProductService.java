package com.caissemagasin.service;

import com.caissemagasin.model.Product;
import com.caissemagasin.repository.ProductRepository;

import java.util.List;

/**
 * Service class for managing products.
 * Provides methods to save, update, and delete products.
 */
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Constructs a ProductService instance with the specified product repository.
     *
     * @param productRepository the repository responsible for managing products
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves the next available product ID based on the existing products.
     * 
     * @return the next product ID
     */
    private int getNextProductId() {
        List<Product> products = productRepository.searchProducts();
        return products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
    }

    /**
     * Saves a new product to the product repository, assigning it a new ID.
     * 
     * @param product the product to save
     * @return true if the product was successfully saved, false otherwise
     */
    public boolean saveProduct(Product product) {
        product.setId(getNextProductId());
        return productRepository.save(product);
    }

    /**
     * Updates an existing product in the product repository.
     * 
     * @param name the name of the product to update
     * @param updatedProduct the updated product details
     */
    public void updateProduct(String name, Product updatedProduct) {
        productRepository.updateProduct(name, updatedProduct);
    }

    /**
     * Deletes a product from the product repository.
     * 
     * @param name the name of the product to delete
     * @return true if the product was successfully deleted, false otherwise
     */
    public boolean deleteProduct(String name) {
        return productRepository.deleteProduct(name);
    }
}
