package com.caissemagasin;


import com.caissemagasin.vue.LoginVue;

public class Main {
    public static void main(String[] args) {
        runApplication();

    }

    private static void runApplication() {
        LoginVue loginVue = new LoginVue();
        loginVue.launchMenu();
    }
}