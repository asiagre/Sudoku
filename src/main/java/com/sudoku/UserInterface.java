package com.sudoku;

import java.util.Scanner;

public class UserInterface {

    private static Scanner scan = new Scanner(System.in);

    public static void introduction() {
        System.out.println("Type the number(format: \"column,row,number\"):");
        System.out.println("If you want to resolve sudoku type SUDOKU");
    }

    public static String takeNumber() {
        String input;
        do {
            introduction();
            input = scan.nextLine();
        } while(!(input.length() == 5 || input.equalsIgnoreCase(SudokuGame.SUDOKU)));
        return input;
    }

}
