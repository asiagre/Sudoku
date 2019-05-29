package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SudokuRow {
    private List<SudokuElement> sudokuElementsRow = new ArrayList<SudokuElement>();

    SudokuRow() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            sudokuElementsRow.add(new SudokuElement(i));
        }
    }

    SudokuRow(List<SudokuElement> list) {
        this.sudokuElementsRow = list;
    }

    List<SudokuElement> getSudokuElementsRow() {
        return sudokuElementsRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuRow sudokuRow = (SudokuRow) o;
        return Objects.equals(sudokuElementsRow, sudokuRow.sudokuElementsRow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sudokuElementsRow);
    }
}
