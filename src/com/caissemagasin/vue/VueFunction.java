package com.caissemagasin.vue;

import java.util.Scanner;


public interface VueFunction {
    Scanner scanner=new Scanner(System.in);
    default void printMessage(String message) {
        System.out.println(message);
    }

    default String scanInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
