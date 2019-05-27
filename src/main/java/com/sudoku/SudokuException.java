package com.sudoku;

class SudokuException extends Exception {

    SudokuException(String message) {
        super(message);
        System.out.println(message);
        System.exit(1);
    }
}
