package com.sudoku;

class UserGame {
    private SudokuGame sudokuGame;

    UserGame(SudokuGame sudokuGame) {
        this.sudokuGame = sudokuGame;
    }

    void playerAddNumber(InputDAO inputDAO) {
        SudokuElement sudokuElement = sudokuGame.getSudokuBoard().getBoard().get(inputDAO.getRow()).getSudokuElementsRow().get(inputDAO.getColumn());
        if(sudokuElement.getValue() == -1) {
            long onTheList = sudokuElement.getListOfPossibleNumbers().stream()
                    .filter(integer -> integer == inputDAO.getNumber())
                    .count();
            if(onTheList == 1) {
                sudokuGame.addNumber(inputDAO);
            } else {
                UserInterface.wrongNumber();
            }
        } else {
            UserInterface.alreadyTaken();
        }

    }
}
