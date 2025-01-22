package com.caissemagasin.vue;

import com.caissemagasin.model.User;

/**
 * The OrderVue class represents the view for managing orders in the system. It holds
 * the user object and provides getter and setter methods for it.
 * 
 * This class implements the VueFunction interface, which defines the structure for view classes.
 */
public class OrderVue implements VueFunction {

    private User user;

    /**
     * Gets the current user associated with this OrderVue.
     * 
     * @return The current User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user for this OrderVue.
     * 
     * @param user The User to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
