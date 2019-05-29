package com.sudoku;

import java.util.*;


public class SudokuGame {
    public static final String SUDOKU = "SUDOKU";
    private SudokuBoard sudokuBoard = new SudokuBoard();
    private ComputerGame computerGame = new ComputerGame(this);
    private UserGame userGame = new UserGame(this);
    private boolean changedBoard;
    private boolean changed = false;
    private Deque<SudokuBoard> stack = new ArrayDeque<>();

    public boolean resolveSudoku() {
        tour();
        return UserInterface.newGameInput();
    }

    private void tour() {
        do {
            String input = UserInterface.takeNumber();
            if(input.length() == 5) {
                String[] values = input.split(",");
                int column = Integer.parseInt(values[0]) - 1;
                int row = Integer.parseInt(values[1]) - 1;
                int number = Integer.parseInt(values[2]);
                userGame.playerAddNumber(new InputDAO(column, row, number));
                System.out.println(sudokuBoard);
            } else if(input.equalsIgnoreCase(SUDOKU)) {
                completeSudoku();
                System.out.println(sudokuBoard);
            }
        } while(!sudokuBoard.isFull());
    }

    private void completeSudoku() {
        do {
            do {
                changedBoard = false;
                changed = false;
                computerGame.fillSudokuWithPossibleNumbers();
            } while(changed || changedBoard);
            try {
                stack.offerFirst(sudokuBoard.deepCopy());
            } catch(CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }
            computerGame.guessValue();
        } while(!sudokuBoard.isFull());
    }

    void changeBoard() throws SudokuException {
        if(stack.size() > 0) {
            SudokuBoard takenBoard = stack.pollFirst();
            for(SudokuRow sudokuRow : takenBoard.getBoard()) {
                for(SudokuElement sudokuElement : sudokuRow.getSudokuElementsRow()) {
                    SudokuElement oldSudokuElement = sudokuBoard.getBoard().get(sudokuElement.getRowNumber()).getSudokuElementsRow().get(sudokuElement.getColumnNumber());
                    if (sudokuElement.getValue() != oldSudokuElement.getValue()) {
                        sudokuElement.getListOfPossibleNumbers().remove((Integer) oldSudokuElement.getValue());
                    }
                }
            }
            sudokuBoard = takenBoard;
            changedBoard = true;
        } else {
            System.out.println(sudokuBoard);
            throw new SudokuException("This sudoku does not have a solution.");
        }
    }

    void addNumber(InputDAO inputDAO) {
        SudokuElement sudokuElement = sudokuBoard.getBoard().get(inputDAO.getRow()).getSudokuElementsRow().get(inputDAO.getColumn());
        sudokuElement.setValue(inputDAO.getNumber());
        removeFromListsPossibleNumbers(sudokuElement);
    }

    private void removeFromListsPossibleNumbers(SudokuElement sudokuElement) {
        int valueOfSudokuElement = sudokuElement.getValue();
        int column = sudokuElement.getColumnNumber();
        int row = sudokuElement.getRowNumber();
        for(SudokuRow sudokuRow : sudokuBoard.getBoard()) {
            for(SudokuElement element : sudokuRow.getSudokuElementsRow()) {
                int inColumn = element.getColumnNumber();
                int inRow = element.getRowNumber();
                if(inColumn == column || inRow == row || ((inRow / 3 == row / 3) && (inColumn / 3 == column / 3))) {
                    element.getListOfPossibleNumbers().removeIf(integer -> integer == valueOfSudokuElement);
                }
            }
        }
    }

    SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    boolean isChangedBoard() {
        return changedBoard;
    }

    void setChanged(boolean changed) {
        this.changed = changed;
    }
}
