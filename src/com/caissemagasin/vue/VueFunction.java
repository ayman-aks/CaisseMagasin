package com.caissemagasin.vue;

import java.util.Scanner;

import static com.caissemagasin.vue.ConsoleUI.*;


interface VueFunction {
    Scanner scanner=new Scanner(System.in);

    default void printMessage(String message) {
        System.out.println(message);
    }

    default String scanInput(String prompt) {
        System.out.print(BLUE+prompt+RESET);
        return scanner.nextLine();
    }

    default void printTitle(String title) {
        clearScreen();
        System.out.print("\033[2J");
        title=title.toUpperCase();
        System.out.println(BOLD + CYAN + "╔══════════════════════════════════════════╗" + RESET);
        System.out.printf(CYAN + "║ %-40s ║%n" + RESET, title);
        System.out.println(CYAN + "╚══════════════════════════════════════════╝" + RESET);
    }

    default void successMessage(String message) {
        System.out.println(GREEN + "✔ " + message + RESET);
    }

    default void errorMessage(String message) {
        System.out.println(RED + "✘ " + message + RESET);
    }

    default void printSeparator() {
        System.out.println(YELLOW + "──────────────────────────────────────────" + RESET);
    }

    default void warningMessage(String message) {
        System.out.println(YELLOW + "✘ " + message + RESET);
    }

    private  void clearScreen() {
        try {
            Thread.sleep(500);
        }catch (InterruptedException e) {
            System.err.println(e);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
