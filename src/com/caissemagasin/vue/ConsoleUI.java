package com.caissemagasin.vue;

public class ConsoleUI {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void printTitle(String title) {
        title=title.toUpperCase();
        System.out.println(BOLD + CYAN + "╔══════════════════════════════════════════╗" + RESET);
        System.out.printf(CYAN + "║ %-40s ║%n" + RESET, title);
        System.out.println(CYAN + "╚══════════════════════════════════════════╝" + RESET);
    }

    public static void successMessage(String message) {
        System.out.println(GREEN + "✔ " + message + RESET);
    }

    public static void errorMessage(String message) {
        System.out.println(RED + "✘ " + message + RESET);
    }

    public static void printSeparator() {
        System.out.println(YELLOW + "──────────────────────────────────────────" + RESET);
    }
}
