package com.sudoku;

public class Application {
    public static void main(String[] args) {
//        SudokuBoard sudokuBoard = new SudokuBoard();
        SudokuGame sudokuGame = new SudokuGame();

//        boolean gameFinished = false;
//
//        while(!gameFinished) {
//
//            SudokuGame theGame = new SudokuGame();
//
//            gameFinished = theGame.resolveSudoku();
//        }

        sudokuGame.tour();
    }
}
