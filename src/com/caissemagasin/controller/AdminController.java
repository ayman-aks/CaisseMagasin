package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.vue.DashboardAdminVue;

/**
 * The AdminController class manages administrative actions such as creating, updating,
 * and deleting users. It interacts with the AdminService and LoginRepository for data operations
 * and communicates with the DashboardAdminVue for user input and output.
 */
public class AdminController {

    private AdminService adminService;
    private LoginRepository loginRepository;
    private DashboardAdminVue dashboardAdminVue;

    /**
     * Constructs an AdminController with the specified service and repository dependencies.
     *
     * @param adminService    the service responsible for user-related operations
     * @param loginRepository the repository responsible for accessing user data
     */
    public AdminController(AdminService adminService, LoginRepository loginRepository) {
        this.adminService = adminService;
        this.loginRepository = loginRepository;
        this.dashboardAdminVue = null;
    }

    /**
     * Creates a new user based on input provided by the DashboardAdminVue.
     * Validates if the login is unique before saving the user.
     */
    public void createUser() {
        dashboardAdminVue.printTitle("Création d'un nouvel utilisateur");

        String name = dashboardAdminVue.scanInput("Nom : ");
        String surname = dashboardAdminVue.scanInput("Prénom : ");
        String login = dashboardAdminVue.scanInput("Login : ");
        String password = "";
        boolean isAdmin = dashboardAdminVue.scanInput("Est-ce un admin ? (true/false) : ").equalsIgnoreCase("true");

        User newUser = new User(0, name, surname, login, password, isAdmin, false);

        if (adminService.saveUser(newUser)) {
            dashboardAdminVue.printMessage("\n=== Utilisateur créé avec succès ===");
        } else {
            dashboardAdminVue.printMessage("\n=== Échec de la création, login déjà existant ===");
        }
    }

    /**
     * Updates the details of an existing user based on input provided by the DashboardAdminVue.
     * If the user is not found, an error message is displayed.
     */
    public void updateUser() {
        dashboardAdminVue.printTitle("Modification d'un utilisateur");
        String login = dashboardAdminVue.scanInput("\nLogin de l'utilisateur à modifier : ");
        User user = loginRepository.findByLogin(login);
        if (user == null) {
            dashboardAdminVue.printMessage("\n=== Échec de la modification, utilisateur non trouvé ===");
        } else {
            String name = dashboardAdminVue.scanInput("Nouveau nom : ");
            String surname = dashboardAdminVue.scanInput("Nouveau prénom : ");
            boolean isAdmin = dashboardAdminVue.scanInput("Est-ce un admin ? (true/false) : ").equalsIgnoreCase("true");

            User updatedUser = new User(user.getId(), name, surname, login, "", isAdmin, false);
            adminService.updateUser(login, updatedUser);
            dashboardAdminVue.printMessage("\n=== Utilisateur modifié avec succès ===");
        }
    }

    /**
     * Deletes a user based on their login, provided by the DashboardAdminVue.
     * Prevents the current user from self-deletion.
     *
     * @param dashboardAdminVue the view instance used for input and output
     */
    public void deleteUser(DashboardAdminVue dashboardAdminVue) {
        dashboardAdminVue.printTitle("Suppression d'un utilisateur");
        String login = dashboardAdminVue.scanInput("\nLogin de l'utilisateur à supprimer : ");
        if (login.equals(dashboardAdminVue.getUser().getLogin())) {
            dashboardAdminVue.printMessage("\n=== Échec de la suppression, vous ne pouvez pas vous auto-supprimer ===");
        } else {
            if (adminService.deleteUser(login)) {
                dashboardAdminVue.printMessage("\n=== Utilisateur supprimé avec succès ===");
            } else {
                dashboardAdminVue.printMessage("\n=== Échec de la suppression, utilisateur non trouvé ===");
            }
        }
    }

    /**
     * Sets the DashboardAdminVue instance for this controller.
     *
     * @param dashboardAdminVue the view instance to be set
     */
    public void setDashboardAdminVue(DashboardAdminVue dashboardAdminVue) {
        this.dashboardAdminVue = dashboardAdminVue;
    }
}
