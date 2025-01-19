package com.caissemagasin.controller;

import com.caissemagasin.model.Product;
import com.caissemagasin.repository.ProductRepository;
import com.caissemagasin.service.ProductService;
import com.caissemagasin.vue.DashboardAdminVue;

public class ProductController {
    private final ProductService productService = new ProductService();
    private final ProductRepository productRepository = new ProductRepository();
    public void createProduct() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        dashboardAdminVue.printMessage("\n=== Création d'un nouveau produit ===");

        String name = dashboardAdminVue.scanInput("Nom du produit : ");
        double price = Double.parseDouble(dashboardAdminVue.scanInput("Prix du produit : "));

        Product newProduct = new Product(0, name, price);

        if (productService.saveProduct(newProduct)) {
            dashboardAdminVue.printMessage("\n=== Produit ajouté avec succès ===");
        } else {
            dashboardAdminVue.printMessage("\n=== Échec de l'ajout, produit déjà existant ===");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    public void updateProduct() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        String name = dashboardAdminVue.scanInput("\nNom du produit à modifier : ");
        Product product = productRepository.findByName(name);
        if (product == null) {
            dashboardAdminVue.printMessage("\n=== Échec de la modification, produit non trouvé ===");
        }else {
            String newName = dashboardAdminVue.scanInput("Nouveau nom du produit : ");
            double price = Double.parseDouble(dashboardAdminVue.scanInput("Nouveau prix : "));
            Product updatedProduct = new Product(0, newName, price);
            productService.updateProduct(name, updatedProduct);
            dashboardAdminVue.printMessage("\n=== Produit modifié avec succès ===");
        }
        dashboardAdminVue.printMenuAdmin();
    }

    public void deleteProduct() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        String name = dashboardAdminVue.scanInput("\nNom du produit à supprimer : ");

        if (productService.deleteProduct(name)) {
            dashboardAdminVue.printMessage("\n=== Produit supprimé avec succès ===");
        } else {
            dashboardAdminVue.printMessage("\n=== Échec de la suppression, produit non trouvé ===");
        }

        dashboardAdminVue.printMenuAdmin();
    }
}
