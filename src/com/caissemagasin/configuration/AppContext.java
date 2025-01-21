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
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.LoginVue;
import com.caissemagasin.vue.OrderVue;

public class AppContext {
    private static AppContext instance;

    private final AdminRepository adminRepository;
    private final LoginRepository loginRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final AdminService adminService;
    private final LoginService loginService;
    private final OrderService orderService;
    private final ProductService productService;

    private final AdminController adminController;
    private final LoginController loginController;
    private final OrderController orderController;
    private final ProductController productController;

    private final DashboardAdminVue dashboardAdminVue;
    private final LoginVue loginVue;
    private final OrderVue orderVue;

    private AppContext() {
        this.adminRepository = new AdminRepository();
        this.loginRepository = new LoginRepository();
        this.orderRepository = new OrderRepository();
        this.productRepository = new ProductRepository();

        this.adminService = new AdminService(this.loginRepository, this.adminRepository);
        this.loginService = new LoginService(this.loginRepository);
        this.orderService = new OrderService(this.orderRepository, this.productRepository);
        this.productService = new ProductService(this.productRepository);

        this.adminController = new AdminController(this.adminService,this.loginRepository);
        this.loginController = new LoginController(this.loginService,this.adminService);
        this.orderController = new OrderController(this.orderService);
        this.productController = new ProductController(this.productService,this.productRepository);

        this.dashboardAdminVue = new DashboardAdminVue(this.adminController,this.productController,this.orderController);
        this.loginVue = new LoginVue(this.loginController);
        this.orderVue=  new OrderVue();

        hydrateViews();
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public AdminRepository getAdminRepository() {
        return adminRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public DashboardAdminVue getDashboardAdminVue() {
        return dashboardAdminVue;
    }

    public LoginVue getLoginVue() {
        return loginVue;
    }

    public OrderVue getOrderVue() {
        return orderVue;
    }

    private void hydrateViews() {
        this.adminController.setDashboardAdminVue(this.dashboardAdminVue);
        this.loginController.setLoginVue(this.loginVue);
        this.loginController.setDashboardAdminVue(this.dashboardAdminVue);
        this.orderController.setOrderVue(this.orderVue);
        this.orderController.setDashboardAdminVue(this.dashboardAdminVue);
        this.productController.setDashboardAdminVue(this.dashboardAdminVue);
    }
}
