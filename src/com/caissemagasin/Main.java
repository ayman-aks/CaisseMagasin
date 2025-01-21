package com.caissemagasin;

import com.caissemagasin.configuration.AppContext;
import com.caissemagasin.vue.LoginVue;

public class Main {
    public static void main(String[] args) {
        AppContext appContext=AppContext.getInstance();
        LoginVue loginVue = appContext.getLoginVue();

        loginVue.launchMenu();
    }
}
