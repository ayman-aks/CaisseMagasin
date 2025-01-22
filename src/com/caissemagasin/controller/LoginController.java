package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.service.LoginService;
import com.caissemagasin.vue.DashboardAdminVue;
import com.caissemagasin.vue.DashboardUserVue;
import com.caissemagasin.vue.LoginVue;

/**
 * The LoginController class handles user login and redirection to appropriate dashboards.
 * It manages both initial user activation and subsequent login attempts, and coordinates
 * with the LoginService, AdminService, and various views.
 */
public class LoginController {

    private LoginService loginService;
    private LoginVue loginVue;
    private DashboardAdminVue dashboardAdminVue;
    private DashboardUserVue dashboardUserVue;
    private AdminService adminService;

    /**
     * Constructs a LoginController with the required service dependencies.
     *
     * @param loginService the service responsible for authentication and user management
     * @param adminService the service responsible for admin-specific operations
     */
    public LoginController(LoginService loginService, AdminService adminService) {
        this.loginService = loginService;
        this.adminService = adminService;
        this.loginVue = null;
        this.dashboardAdminVue = null;
        this.dashboardUserVue = null;
    }

    /**
     * Handles the login process, including first-time activation and authentication.
     */
    public void doLogin() {
        String login = loginVue.scanInput("Login de l'utilisateur : ");

        // Check if the user is active (first-time login scenario)
        User user = loginService.isActive(login);
        if (user != null && !user.getActive()) {
            loginVue.printMessage("C'est votre première connexion, tapez votre mot de passe.");
            String password = loginVue.scanInput("Mot de passe : ");
            String passwordConfirm = loginVue.scanInput("Confirmation du mot de passe : ");
            if (password.equals(passwordConfirm)) {
                user.setPassword(password);
                user.setActive(true);
                adminService.updateUser(login, user);
                loginVue.successMessage("Connexion réussie !");
                redirectToMenu(user);
                return;
            } else {
                loginVue.errorMessage("Les deux mots de passe ne correspondent pas.");
                System.exit(0);
            }
        }

        // Standard authentication for existing active users
        String password = loginVue.scanInput("Mot de passe : ");

        if (loginService.authenticationUser(user, password)) {
            loginVue.successMessage("Connexion réussie !");
            redirectToMenu(user);
        } else {
            loginVue.errorMessage("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    /**
     * Redirects the authenticated user to the appropriate menu based on their role.
     *
     * @param user the authenticated user
     */
    private void redirectToMenu(User user) {
        if (user.getAdmin()) {
            dashboardAdminVue.setUser(user);
            dashboardAdminVue.printMenuAdmin();
        } else {
            dashboardUserVue.setUser(user);
            dashboardUserVue.printMenuUser();
        }
    }

    /**
     * Sets the DashboardAdminVue instance for this controller.
     *
     * @param dashboardAdminVue the admin dashboard view instance
     */
    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }

    /**
     * Sets the LoginVue instance for this controller.
     *
     * @param loginVue the login view instance
     */
    public void setLoginVue(LoginVue loginVue) {
        this.loginVue = loginVue;
    }

    /**
     * Sets the DashboardUserVue instance for this controller.
     *
     * @param dashboardUserVue the user dashboard view instance
     */
    public void setDashboardUserVue(DashboardUserVue dashboardUserVue) {
        this.dashboardUserVue = dashboardUserVue;
    }
}
