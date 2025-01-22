package com.caissemagasin.vue;

import com.caissemagasin.model.User;

/**
 * The DashboardVue class serves as an abstract base class for creating various dashboard views
 * for different types of users (e.g., admin, regular user). It manages the user associated with
 * the dashboard and provides getter and setter methods to access and update the user.
 * 
 * This class is intended to be extended by more specific dashboard views, such as the user or
 * admin dashboard.
 */
public abstract class DashboardVue {

    protected User user;

    /**
     * Gets the user associated with this dashboard.
     * 
     * @return The user associated with this dashboard.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user for this dashboard.
     * 
     * @param user The user to be associated with this dashboard.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
