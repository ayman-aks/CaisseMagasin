package com.caissemagasin.service;

import com.caissemagasin.model.Order;
import com.caissemagasin.model.Product;
import com.caissemagasin.model.User;
import com.caissemagasin.repository.OrderRepository;
import com.caissemagasin.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing orders and products.
 * Provides methods to create new orders, search for products, finalize orders, and retrieve orders by ID.
 */
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * Constructs an OrderService instance with the specified order and product repositories.
     *
     * @param orderRepository the repository responsible for managing orders
     * @param productRepository the repository responsible for managing products
     */
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * Creates a new order with the next available order ID.
     * 
     * @return a new Order object with the next order ID
     */
    public Order createNewOrder() {
        return new Order(orderRepository.getNextOrderId());
    }

    /**
     * Searches for products based on a keyword, with pagination support.
     * 
     * @param keyword the keyword to search for in product names
     * @param page the page number for pagination (starting from 0)
     * @param pageSize the number of products per page
     * @return a map containing the list of products found and the total number of products matching the keyword
     */
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

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the ID of the product to retrieve
     * @return the product if found, null otherwise
     */
    public Product getProductById(int id) {
        return productRepository.searchProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Finalizes an order by saving it through the order repository.
     * 
     * @param order the order to be finalized
     * @param user the user who is finalizing the order
     * @return true if the order was successfully saved, false otherwise
     */
    public boolean finalizeOrder(Order order, User user) {
        return orderRepository.saveOrder(order, user);
    }

    /**
     * Searches for an order by its ID.
     * 
     * @param id the ID of the order to search for
     * @return the order details as a string if found, null otherwise
     */
    public String searchOrder(String id) {
        return orderRepository.searchOrderById(id);
    }
}
