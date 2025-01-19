package com.caissemagasin.service;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.repository.OrderRepository;
import com.caissemagasin.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();
    private final ProductRepository productRepository = new ProductRepository();

    public Order createNewOrder() {
        return new Order(orderRepository.getNextOrderId());
    }

    public List<Product> searchProducts(String keyword, int page, int pageSize) {
        List<Product> allProducts = productRepository.searchProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (allProducts.isEmpty()) {
            return List.of();
        }

        int start = page * pageSize;
        int end = Math.min(start + pageSize, allProducts.size());

        if (start >= allProducts.size()) {
            start = 0;
            end = Math.min(pageSize, allProducts.size());
        }

        return allProducts.subList(start, end);
    }

    public Product getProductById(int id) {
        return productRepository.searchProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean finalizeOrder(Order order) {
        return orderRepository.saveOrder(order);
    }
}
