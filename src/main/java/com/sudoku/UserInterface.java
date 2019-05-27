package com.sudoku;

import java.util.Scanner;

class UserInterface {

    private static Scanner scan = new Scanner(System.in);

    static String takeNumber() {
        String input;
        do {
            introduction();
            input = scan.nextLine();
        } while(!(validateInput(input) || input.equalsIgnoreCase(SudokuGame.SUDOKU)));
        return input;
    }

    private static void introduction() {
        System.out.println("Type the number - format: \"column,row,number\"");
        System.out.println("(if you want to resolve sudoku type SUDOKU):");
    }

    private static boolean validateInput(String input) {
        return input.matches("[1-9],[1-9],[1-9]");
    }

    static void wrongNumber() {
        System.out.println("You cannot put this number in that place");
    }

    static void alreadyTaken() {
        System.out.println("This place is already taken. Please try again.");
    }

    private static void newGame() {
        System.out.println("New game? y/n");
    }

    static boolean newGameInput() {
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
