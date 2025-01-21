package com.caissemagasin.controller;

import com.caissemagasin.model.Product;
import com.caissemagasin.repository.ProductRepository;
import com.caissemagasin.service.ProductService;
import com.caissemagasin.vue.DashboardAdminVue;

public class ProductController {
    private  ProductService productService;
    private  ProductRepository productRepository;
    private  DashboardAdminVue dashboardAdminVue;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.dashboardAdminVue = null;
    }

    public void createProduct() {
        dashboardAdminVue.successMessage("Création d'un nouveau produit");

        String name = dashboardAdminVue.scanInput("Nom du produit : ");
        double price = Double.parseDouble(dashboardAdminVue.scanInput("Prix du produit : "));

        Product newProduct = new Product(0, name, price);

        if (productService.saveProduct(newProduct)) {
            dashboardAdminVue.successMessage("Produit ajouté avec succès");
        } else {
            dashboardAdminVue.warningMessage("Échec de l'ajout, produit déjà existant");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    public void updateProduct() {
        String name = dashboardAdminVue.scanInput("\nNom du produit à modifier : ");
        Product product = productRepository.findByName(name);
        if (product == null) {
            dashboardAdminVue.errorMessage("Échec de la modification, produit non trouvé");
        } else {
            String newName = dashboardAdminVue.scanInput("Nouveau nom du produit : ");
            double price = Double.parseDouble(dashboardAdminVue.scanInput("Nouveau prix : "));
            Product updatedProduct = new Product(0, newName, price);
            productService.updateProduct(name, updatedProduct);
            dashboardAdminVue.successMessage("Produit modifié avec succès");
        }
        dashboardAdminVue.printMenuAdmin();
    }

    public void deleteProduct() {
        String name = dashboardAdminVue.scanInput("\nNom du produit à supprimer : ");

        if (productService.deleteProduct(name)) {
            dashboardAdminVue.successMessage("Produit supprimé avec succès");
        } else {
            dashboardAdminVue.successMessage("Échec de la suppression, produit non trouvé");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }
}
