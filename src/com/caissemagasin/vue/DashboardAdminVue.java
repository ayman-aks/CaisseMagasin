package com.caissemagasin.vue;

import com.caissemagasin.controller.AdminController;
import com.caissemagasin.controller.OrderController;
import com.caissemagasin.controller.ProductController;

/**
 * The DashboardAdminVue class represents the admin dashboard interface in the console application.
 * It handles displaying the menu options for administrators and invoking the corresponding actions
 * through the provided controllers.
 * 
 * This class extends {@link DashboardVue} and implements {@link VueFunction}.
 */
public class DashboardAdminVue extends DashboardVue implements VueFunction {

    private final AdminController adminController;
    private final ProductController productController;
    private final OrderController orderController;

    /**
     * Constructs a new instance of DashboardAdminVue with the given controllers.
     * 
     * @param adminController The controller for managing users.
     * @param productController The controller for managing products.
     * @param orderController The controller for managing orders.
     */
    public DashboardAdminVue(AdminController adminController, ProductController productController, OrderController orderController) {
        this.adminController = adminController;
        this.productController = productController;
        this.orderController = orderController;
    }

    /**
     * Displays the admin menu and allows the admin to select various actions such as
     * creating, modifying, or deleting users and products, as well as initiating and searching for orders.
     */
    public void printMenuAdmin() {
        while (true) {
            printTitle("ADMINISTRATOR MENU");

            printMessage(ConsoleUI.BLUE + "[1] Create a user" + ConsoleUI.RESET);
            printMessage(ConsoleUI.BLUE + "[2] Update a user" + ConsoleUI.RESET);
            printMessage(ConsoleUI.BLUE + "[3] Delete a user" + ConsoleUI.RESET);

            printMessage(ConsoleUI.CYAN + "[4] Add a product" + ConsoleUI.RESET);
            printMessage(ConsoleUI.CYAN + "[5] Update a product" + ConsoleUI.RESET);
            printMessage(ConsoleUI.CYAN + "[6] Delete a product" + ConsoleUI.RESET);

            printMessage(ConsoleUI.YELLOW + "[7] Initiate an order" + ConsoleUI.RESET);
            printMessage(ConsoleUI.YELLOW + "[8] Search for an order" + ConsoleUI.RESET);

            printMessage(ConsoleUI.RED + "[9] Logout" + ConsoleUI.RESET);

            printSeparator();
            String choice = scanInput(ConsoleUI.YELLOW + "Your choice: " + ConsoleUI.RESET);

            switch (choice) {
                case "1":
                    adminController.createUser();
                    break;
                case "2":
                    adminController.updateUser();
                    break;
                case "3":
                    adminController.deleteUser(this);
                    break;
                case "4":
                    productController.createProduct();
                    break;
                case "5":
                    productController.updateProduct();
                    break;
                case "6":
                    productController.deleteProduct();
                    break;
                case "7":
                    orderController.initiateOrder(user);
                    break;
                case "8":
                    orderController.searchOrder(user);
                    break;
                case "9":
                    successMessage("Logout successful!");
                    System.exit(1);
                default:
                    errorMessage("Invalid option!");
            }
        }
    }
}
