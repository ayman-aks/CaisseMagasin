
package com.caissemagasin.controller;

import com.caissemagasin.model.Product;
import com.caissemagasin.repository.ProductRepository;
import com.caissemagasin.service.ProductService;
import com.caissemagasin.vue.DashboardAdminVue;
/**
 * Controller class to manage product-related operations within the application.
 * This class serves as a bridge between the product service, repository, and admin dashboard view.
 */
public class ProductController {

    /**
     * Service for managing product-related business logic.
     */
    private ProductService productService;

    /**
     * Repository for accessing product data.
     */
    private ProductRepository productRepository;

    /**
     * View for displaying the admin dashboard interface.
     */
    private DashboardAdminVue dashboardAdminVue;

    /**
     * Constructor for initializing the ProductController with required services and repositories.
     *
     * @param productService    Service for product management.
     * @param productRepository Repository for product data access.
     */
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.dashboardAdminVue = null;
    }

    /**
     * Handles the creation of a new product by collecting input from the admin
     * and using the ProductService to save the product.
     */
    public void createProduct() {
        dashboardAdminVue.printTitle("Création d'un nouveau produit");

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

    /**
     * Handles the update of an existing product. It retrieves the product by name,
     * collects new details from the admin, and updates the product.
     */
    public void updateProduct() {
        dashboardAdminVue.printTitle("Modification d'un produit");
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

    /**
     * Handles the deletion of a product by its name. It interacts with the ProductService
     * to perform the deletion and displays appropriate feedback.
     */
    public void deleteProduct() {
        dashboardAdminVue.printTitle("Suppression d'un produit");
        String name = dashboardAdminVue.scanInput("\nNom du produit à supprimer : ");

        if (productService.deleteProduct(name)) {
            dashboardAdminVue.successMessage("Produit supprimé avec succès");
        } else {
            dashboardAdminVue.successMessage("Échec de la suppression, produit non trouvé");
        }

        dashboardAdminVue.printMenuAdmin();
    }

    /**
     * Sets the admin dashboard view for the controller.
     *
     * @param dashboardAdminVue View for the admin dashboard.
     */
    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }
}
