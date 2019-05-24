package com.sudoku;

import java.util.Scanner;

public class UserInterface {

    private static Scanner scan = new Scanner(System.in);

    public static String takeNumber() {
        String input;
        do {
            introduction();
            input = scan.nextLine();
        } while(!(validateInput(input) || input.equalsIgnoreCase(SudokuGame.SUDOKU)));
        return input;
    }

    private static void introduction() {
        System.out.println("Type the number(format: \"column,row,number\"):");
        System.out.println("If you want to resolve sudoku type SUDOKU");
    }

    private static boolean validateInput(String input) {
        return input.matches("[1-9],[1-9],[1-9]");
    }

    public static void wrongNumber() {
        System.out.println("You cannot put this number in that place");
    }

    public static void alreadyTaken() {
        System.out.println("This place is already taken. Please try again.");
    }

    public static void newGame() {
        System.out.println("New game? y/n");
    }

    public static boolean newGameInput() {
        String input;
        do{
            newGame();
            input = scan.nextLine();
        } while(!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")));
        if(input.equalsIgnoreCase("n")) {
            return true;
        }
        return false;
    }

}
