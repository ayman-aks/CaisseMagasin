package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.vue.AdminVue;
import com.caissemagasin.vue.DashboardAdminVue;

/**
 * The AdminController class manages administrative actions such as creating, updating,
 * and deleting users. It interacts with the AdminService and LoginRepository for data operations
 * and communicates with the DashboardAdminVue for user input and output.
 */
public class AdminController {

    private AdminService adminService;
    private LoginRepository loginRepository;
    private AdminVue adminVue;

    /**
     * Constructs an AdminController with the specified service and repository dependencies.
     *
     * @param adminService    the service responsible for user-related operations
     * @param loginRepository the repository responsible for accessing user data
     */
    public AdminController(AdminService adminService, LoginRepository loginRepository, AdminVue adminVue) {
        this.adminService = adminService;
        this.loginRepository = loginRepository;
        this.adminVue = adminVue;
    }

    /**
     * Creates a new user based on input provided by the DashboardAdminVue.
     * Validates if the login is unique before saving the user.
     */
    public void createUser() {
        adminVue.printTitle("Création d'un nouvel utilisateur");

        String name = adminVue.scanInput("Nom : ");
        String surname = adminVue.scanInput("Prénom : ");
        String login = adminVue.scanInput("Login : ");
        String password = "";
        boolean isAdmin = adminVue.scanInput("Est-ce un admin ? (true/false) : ").equalsIgnoreCase("true");

        User newUser = new User(0, name, surname, login, password, isAdmin, false);

        if (adminService.saveUser(newUser)) {
            adminVue.printMessage("\n=== Utilisateur créé avec succès ===");
        } else {
            adminVue.printMessage("\n=== Échec de la création, login déjà existant ===");
        }
    }

    /**
     * Updates the details of an existing user based on input provided by the DashboardAdminVue.
     * If the user is not found, an error message is displayed.
     */
    public void updateUser() {
        adminVue.printTitle("Modification d'un utilisateur");
        String login = adminVue.scanInput("\nLogin de l'utilisateur à modifier : ");
        User user = loginRepository.findByLogin(login);
        if (user == null) {
            adminVue.printMessage("\n=== Échec de la modification, utilisateur non trouvé ===");
        } else {
            String name = adminVue.scanInput("Nouveau nom : ");
            String surname = adminVue.scanInput("Nouveau prénom : ");
            boolean isAdmin = adminVue.scanInput("Est-ce un admin ? (true/false) : ").equalsIgnoreCase("true");

            User updatedUser = new User(user.getId(), name, surname, login, "", isAdmin, false);
            adminService.updateUser(login, updatedUser);
            adminVue.printMessage("\n=== Utilisateur modifié avec succès ===");
        }
    }

    /**
     * Deletes a user based on their login, provided by the DashboardAdminVue.
     * Prevents the current user from self-deletion.
     *
     * @param adminVue the view instance used for input and output
     */
    public void deleteUser(DashboardAdminVue adminVue) {
        adminVue.printTitle("Suppression d'un utilisateur");
        String login = adminVue.scanInput("\nLogin de l'utilisateur à supprimer : ");
        if (login.equals(adminVue.getUser().getLogin())) {
            adminVue.printMessage("\n=== Échec de la suppression, vous ne pouvez pas vous auto-supprimer ===");
        } else {
            if (adminService.deleteUser(login)) {
                adminVue.printMessage("\n=== Utilisateur supprimé avec succès ===");
            } else {
                adminVue.printMessage("\n=== Échec de la suppression, utilisateur non trouvé ===");
            }
        }
    }
}
