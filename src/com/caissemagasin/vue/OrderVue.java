package com.caissemagasin.vue;

import com.caissemagasin.model.User;

public class OrderVue implements VueFunction{
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
