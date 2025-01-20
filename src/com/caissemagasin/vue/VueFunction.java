package com.caissemagasin.vue;

import java.util.Scanner;

import static com.caissemagasin.vue.ConsoleUI.GREEN;
import static com.caissemagasin.vue.ConsoleUI.RESET;


public interface VueFunction {
    Scanner scanner=new Scanner(System.in);
    default void printMessage(String message) {
        System.out.println(message);
    }

    default String scanInput(String prompt) {
        System.out.print(GREEN+prompt+RESET);
        return scanner.nextLine();
    }
}
