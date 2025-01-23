
package com.caissemagasin.controller;

import com.caissemagasin.model.Product;
import com.caissemagasin.service.ProductService;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.ProductVue;

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
     * View for displaying the product view.
     */
    private ProductVue productVue;

    /**
     * Constructor for initializing the ProductController with required services and repositories.
     *
     * @param productService    Service for product management.
     */
    public ProductController(ProductService productService,ProductVue productVue) {
        this.productService = productService;
        this.productVue = productVue;
    }

    /**
     * Handles the creation of a new product by collecting input from the admin
     * and using the ProductService to save the product.
     */
    public void createProduct() {
        productVue.printTitle("Création d'un nouveau produit");

        String name = productVue.scanInput("Nom du produit : ");
        double price = Double.parseDouble(productVue.scanInput("Prix du produit : "));

        Product newProduct = new Product(0, name, price);

        if (productService.saveProduct(newProduct)) {
            productVue.successMessage("Produit ajouté avec succès");
        } else {
            productVue.warningMessage("Échec de l'ajout, produit déjà existant");
        }

    }

    /**
     * Handles the update of an existing product. It retrieves the product by name,
     * collects new details from the admin, and updates the product.
     */
    public void updateProduct() {
        productVue.printTitle("Modification d'un produit");
        String name = productVue.scanInput("\nNom du produit à modifier : ");
        Product product = productService.findByName(name);
        if (product == null) {
            productVue.errorMessage("Échec de la modification, produit non trouvé");
        } else {
            String newName = productVue.scanInput("Nouveau nom du produit : ");
            double price = Double.parseDouble(productVue.scanInput("Nouveau prix : "));
            Product updatedProduct = new Product(0, newName, price);
            productService.updateProduct(name, updatedProduct);
            productVue.successMessage("Produit modifié avec succès");
        }
    }

    /**
     * Handles the deletion of a product by its name. It interacts with the ProductService
     * to perform the deletion and displays appropriate feedback.
     */
    public void deleteProduct() {
        productVue.printTitle("Suppression d'un produit");
        String name = productVue.scanInput("\nNom du produit à supprimer : ");

        if (productService.deleteProduct(name)) {
            productVue.successMessage("Produit supprimé avec succès");
        } else {
            productVue.successMessage("Échec de la suppression, produit non trouvé");
        }
    }
}
