package com.caissemagasin.service;

import com.caissemagasin.model.Product;
import com.caissemagasin.repository.ProductRepository;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository = new ProductRepository();

    private int getNextProductId() {
        List<Product> products = productRepository.searchProducts();
        return products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
    }

    public boolean saveProduct(Product product) {
        product.setId(getNextProductId());
        return productRepository.save(product);
    }

    public void updateProduct(String name, Product updatedProduct) {
        productRepository.updateProduct(name, updatedProduct);
    }

    public boolean deleteProduct(String name) {
        return productRepository.deleteProduct(name);
    }
}
