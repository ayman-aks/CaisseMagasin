package com.caissemagasin.service;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.repository.OrderRepository;
import com.caissemagasin.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createNewOrder() {
        return new Order(orderRepository.getNextOrderId());
    }

    public Map<String, ?> searchProducts(String keyword, int page, int pageSize) {
        List<Product> allProducts = productRepository.searchProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();

        if (allProducts.isEmpty()) {
            map.put("products", List.of());
            map.put("nbrProducts", 0);
            return map;
        }

        int start = page * pageSize;
        int end = Math.min(start + pageSize, allProducts.size());

        if (start >= allProducts.size()) {
            start = 0;
            end = Math.min(pageSize, allProducts.size());
        }

        map.put("products", allProducts.subList(start, end));
        map.put("nbrProducts", allProducts.size());
        return map;
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
