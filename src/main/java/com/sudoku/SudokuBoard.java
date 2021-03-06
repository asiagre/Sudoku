package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SudokuBoard extends Prototype {
    public final static int MIN_INDEX = 0;
    public final static int MAX_INDEX = 8;
    private List<SudokuRow> board = new ArrayList<>();

    SudokuBoard() {
        for(int i = MIN_INDEX; i <= MAX_INDEX; i++) {
            board.add(new SudokuRow());
            for(SudokuElement sudokuElement : board.get(i).getSudokuElementsRow()) {
                sudokuElement.setRowNumber(i);
            }
        }
    }

    boolean isFull() {
        long howManyEmpty = board.stream()
                .flatMap(sudokuRow -> sudokuRow.getSudokuElementsRow().stream())
                .filter(sudokuElement -> sudokuElement.getValue() == -1)
                .count();
        if(howManyEmpty > 0) {
            return false;
        }
        return true;
    }

    SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = (SudokuBoard)super.clone();
        clonedBoard.board = new ArrayList<>();
        for(SudokuRow sudokuRow : board) {
            List<SudokuElement> clonedListOfSudokuElements = sudokuRow.getSudokuElementsRow().stream()
                    .map(sudokuElement -> new SudokuElement(new ArrayList<Integer>(sudokuElement.getListOfPossibleNumbers()), sudokuElement.getValue(), sudokuElement.getColumnNumber(), sudokuElement.getRowNumber()))
                    .collect(Collectors.toList());
            SudokuRow clonedSudokuRow = new SudokuRow(clonedListOfSudokuElements);
            clonedBoard.getBoard().add(clonedSudokuRow);
        }
        return clonedBoard;
    }

    @Override
    public String toString() {
        String result = "";
        if(board.size() > 0) {
            String border = " -----------------------------------";
            result += border + "\n";
            for (int i = MIN_INDEX; i <= MAX_INDEX; i++) {
                result += "| ";
                for (int j = MIN_INDEX; j <= MAX_INDEX; j++) {
                    SudokuElement sudokuElement = board.get(i).getSudokuElementsRow().get(j);
                    if (sudokuElement.getValue() != -1) {
                        result += sudokuElement.toString() + " | ";
                    } else {
                        result += "  | ";
                    }
                }
                result += "\n" + border + "\n";
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        return board.equals(that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    List<SudokuRow> getBoard() {
        return board;
    }
}
