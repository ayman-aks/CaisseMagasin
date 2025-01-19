package com.caissemagasin.controller;

import com.caissemagasin.model.User;
import com.caissemagasin.repository.LoginRepository;
import com.caissemagasin.service.AdminService;
import com.caissemagasin.vue.DashboardAdminVue;

public class AdminController {
    private final AdminService adminService = new AdminService();
    private final LoginRepository loginRepository = new LoginRepository();

    public void createUser() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        dashboardAdminVue.printMessage("\n=== Création d'un nouvel utilisateur ===");

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

        dashboardAdminVue.printMenuAdmin();
    }

    public void updateUser() {
        DashboardAdminVue dashboardAdminVue = new DashboardAdminVue();
        String login = dashboardAdminVue.scanInput("\nLogin de l'utilisateur à modifier : ");
        User user=loginRepository.findByLogin(login);
        if(user==null) {
            dashboardAdminVue.printMessage("\n=== Échec de la modification, utilisateur non trouvé ===");

        }else {
            String name = dashboardAdminVue.scanInput("Nouveau nom : ");
            String surname = dashboardAdminVue.scanInput("Nouveau prénom : ");
            boolean isAdmin = dashboardAdminVue.scanInput("Est-ce un admin ? (true/false) : ").equalsIgnoreCase("true");

            User updatedUser = new User(user.getId(), name, surname, login, "", isAdmin, user.getActive());
            adminService.updateUser(login, updatedUser);
            dashboardAdminVue.printMessage("\n=== Utilisateur modifié avec succès ===");
        }


        dashboardAdminVue.printMenuAdmin();
    }

    public void deleteUser(DashboardAdminVue dashboardAdminVue) {
        String login = dashboardAdminVue.scanInput("\nLogin de l'utilisateur à supprimer : ");
        if (login.equals(dashboardAdminVue.getUser().getLogin())) {
            dashboardAdminVue.printMessage("\n=== Echec de la suppression, vous pouvez pas vous auto-supprimé ===");
        }else {
            if (adminService.deleteUser(login)) {
                dashboardAdminVue.printMessage("\n=== Utilisateur supprimé avec succès ===");
            } else {
                dashboardAdminVue.printMessage("\n=== Échec de la suppression, utilisateur non trouvé ===");
            }
        }

        dashboardAdminVue.printMenuAdmin();
    }
}
