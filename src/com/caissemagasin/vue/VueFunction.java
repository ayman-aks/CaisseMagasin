package com.caissemagasin.vue;

import java.util.Scanner;

import static com.caissemagasin.vue.ConsoleUI.*;

/**
 * The VueFunction interface defines the common methods for the views (UI components)
 * in the application. These methods handle user interaction through console input and output.
 * The interface provides methods for printing messages, capturing user input, and displaying
 * success, error, and warning messages.
 * 
 * This interface is meant to be implemented by view classes, which will provide the
 * actual functionality for specific UI screens.
 */
interface VueFunction {
    Scanner scanner = new Scanner(System.in);

    /**
     * Prints the provided message to the console.
     *
     * @param message The message to be printed.
     */
    default void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user with the provided prompt and captures the user input.
     *
     * @param prompt The prompt message to be displayed to the user.
     * @return The input provided by the user.
     */
    default String scanInput(String prompt) {
        System.out.print(BLUE + prompt + RESET);
        return scanner.nextLine();
    }

    /**
     * Prints a formatted title to the console, clearing the screen before displaying it.
     *
     * @param title The title to be printed.
     */
    default void printTitle(String title) {
        clearScreen();
        System.out.print("\033[2J");
        title = title.toUpperCase();
        System.out.println(BOLD + CYAN + "╔══════════════════════════════════════════╗" + RESET);
        System.out.printf(CYAN + "║ %-40s ║%n" + RESET, title);
        System.out.println(CYAN + "╚══════════════════════════════════════════╝" + RESET);
    }

    /**
     * Prints a success message to the console, using a check mark (✔).
     *
     * @param message The success message to be printed.
     */
    default void successMessage(String message) {
        System.out.println(GREEN + "✔ " + message + RESET);
    }

    /**
     * Prints an error message to the console, using a cross mark (✘).
     *
     * @param message The error message to be printed.
     */
    default void errorMessage(String message) {
        System.out.println(RED + "✘ " + message + RESET);
    }

    /**
     * Prints a separator line to the console.
     */
    default void printSeparator() {
        System.out.println(YELLOW + "──────────────────────────────────────────" + RESET);
    }

    /**
     * Prints a warning message to the console, using a warning symbol (✘).
     *
     * @param message The warning message to be printed.
     */
    default void warningMessage(String message) {
        System.out.println(YELLOW + "✘ " + message + RESET);
    }

    /**
     * Clears the console screen with a delay for visual effect.
     */
    private void clearScreen() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
