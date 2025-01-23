package com.caissemagasin.configuration;

import com.caissemagasin.controller.AdminController;
import com.caissemagasin.controller.LoginController;
import com.caissemagasin.controller.OrderController;
import com.caissemagasin.controller.ProductController;
import com.caissemagasin.repository.AdminRepository;
import com.caissemagasin.repository.LoginRepository;
import com.caissemagasin.repository.OrderRepository;
import com.caissemagasin.repository.ProductRepository;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.service.LoginService;
import com.caissemagasin.service.OrderService;
import com.caissemagasin.service.ProductService;
import com.caissemagasin.vue.*;

/**
 * AppContext is a singleton class that acts as the central configuration and dependency injection container
 * for the application. It initializes and manages instances of repositories, services, controllers, and views.
 * This ensures proper dependency management and avoids redundant object creation.
 */
public class AppContext {
    /**
     * The single instance of AppContext.
     */
    private static AppContext instance;

    // Repository instances
    private final AdminRepository adminRepository;
    private final LoginRepository loginRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Service instances
    private final AdminService adminService;
    private final LoginService loginService;
    private final OrderService orderService;
    private final ProductService productService;

    // Controller instances
    private final AdminController adminController;
    private final LoginController loginController;
    private final OrderController orderController;
    private final ProductController productController;

    // View instances
    private final DashboardAdminVue dashboardAdminVue;
    private final LoginVue loginVue;
    private final OrderVue orderVue;
    private final DashboardUserVue dashboardUserVue;
    private final ProductVue productVue;

    /**
     * Private constructor to initialize and wire dependencies.
     * Prevents direct instantiation from outside the class.
     */
    private AppContext() {
        // Initialize repositories
        this.adminRepository = new AdminRepository();
        this.loginRepository = new LoginRepository();
        this.orderRepository = new OrderRepository();
        this.productRepository = new ProductRepository();

        // Initialize services with dependencies
        this.adminService = new AdminService(this.loginRepository, this.adminRepository);
        this.loginService = new LoginService(this.loginRepository);
        this.orderService = new OrderService(this.orderRepository, this.productRepository);
        this.productService = new ProductService(this.productRepository);

        // Initialize controllers with dependencies
        this.adminController = new AdminController(this.adminService, this.loginRepository);
        this.loginController = new LoginController(this.loginService, this.adminService);
        this.orderController = new OrderController(this.orderService);

        this.productVue = new ProductVue();
        this.productController = new ProductController(this.productService,this.productVue);

        // Initialize views with controllers
        this.dashboardAdminVue = new DashboardAdminVue(this.adminController, this.productController, this.orderController);
        this.loginVue = new LoginVue(this.loginController);
        this.orderVue = new OrderVue();
        this.dashboardUserVue = new DashboardUserVue(this.orderController);

        // Configure views with their corresponding controllers
        hydrateViews();
    }

    /**
     * Returns the singleton instance of AppContext.
     * If the instance does not exist, it is created.
     *
     * @return the AppContext instance
     */
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    /**
     * Provides access to the LoginVue instance.
     *
     * @return the LoginVue instance
     */
    public LoginVue getLoginVue() {
        return loginVue;
    }

    /**
     * Configures the views by setting the required controller instances.
     */
    private void hydrateViews() {
        this.adminController.setDashboardAdminVue(this.dashboardAdminVue);
        this.loginController.setLoginVue(this.loginVue);
        this.loginController.setDashboardAdminVue(this.dashboardAdminVue);
        this.loginController.setDashboardUserVue(this.dashboardUserVue);
        this.orderController.setOrderVue(this.orderVue);
        this.orderController.setDashboardAdminVue(this.dashboardAdminVue);
        this.orderController.setDashboardUserVue(this.dashboardUserVue);
    }
}
