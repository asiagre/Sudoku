package com.sudoku;

import java.util.ArrayDeque;
import java.util.Deque;


public class SudokuGame {
    public static final String SUDOKU = "SUDOKU";
    private SudokuBoard sudokuBoard;
    private Logic logic;
    private Deque<SudokuBoard> stack = new ArrayDeque<>();

    public SudokuGame(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.logic = new Logic(this);
    }

    public void tour() {
        do {
            String input = UserInterface.takeNumber();
            if(input.length() == 5) {
                String[] values = input.split(",");
                int column = Integer.parseInt(values[0]) - 1;
                int row = Integer.parseInt(values[1]) - 1;
                int number = Integer.parseInt(values[2]);
                addNumber(new InputDAO(column, row, number));
                System.out.println(sudokuBoard);
            } else if(input.equalsIgnoreCase(SUDOKU)) {
                completeSudoku();
                System.out.println(sudokuBoard);
            }
        } while(!sudokuBoard.isFull());
    }

    public boolean resolveSudoku() {
        return false;
    }

    public void completeSudoku() {
        do {
            try {
                stack.offer(sudokuBoard.deepCopy());
            } catch(CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }
            do {
                logic.setChanged(false);
                logic.checkingSudokuRow();
                logic.checkingSudokuColumn();
                logic.checkingSudokuSector();
            } while(logic.isChanged());
            try {
                stack.offer(sudokuBoard.deepCopy());
            } catch(CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }
            logic.guessValue();
        } while(!sudokuBoard.isFull());
    }

    public void addNumber(InputDAO inputDAO) {
        sudokuBoard.getBoard().get(inputDAO.getRow()).getSudokuElementsRow().get(inputDAO.getColumn()).setValue(inputDAO.getNumber());
    }

    public void changedElement() throws SudokuException {
        if(stack.size() > 0) {
            SudokuBoard takenBoard = stack.poll();
            System.out.println(takenBoard);
            for(SudokuRow sudokuRow : takenBoard.getBoard()) {
                for(SudokuElement sudokuElement : sudokuRow.getSudokuElementsRow()) {
                    if (sudokuElement.getValue() != sudokuBoard.getBoard().get(sudokuElement.getRowNumber()).getSudokuElementsRow().get(sudokuElement.getColumnNumber()).getValue()) {
                        sudokuElement.getListOfPossibleNumbers().remove((Integer) sudokuBoard.getBoard().get(sudokuElement.getRowNumber()).getSudokuElementsRow().get(sudokuElement.getColumnNumber()).getValue());
                        sudokuBoard = takenBoard;
                    }
                }
            }
        } else {
            System.out.println(sudokuBoard);
            System.exit(1);
            throw new SudokuException("Exception - stack is empty.");
        }
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public Logic getLogic() {
        return logic;
    }

    public Deque<SudokuBoard> getStack() {
        return stack;
    }

}
