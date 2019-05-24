package com.sudoku;

public class SudokuException extends Exception {

    public SudokuException() {
    }

    public SudokuException(String message) {
        super(message);
        System.out.println(message);
        System.exit(1);
    }
}
