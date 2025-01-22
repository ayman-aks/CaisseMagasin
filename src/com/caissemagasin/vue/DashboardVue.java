package com.caissemagasin.vue;

import com.caissemagasin.model.User;

public abstract class DashboardVue {
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
