package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SudokuElement {
    public static final int EMPTY = -1;
    private int value = EMPTY;
    private List<Integer> listOfPossibleNumbers;
    private int columnNumber;
    private int rowNumber;

    SudokuElement(int columnNumber) {
        this.columnNumber = columnNumber;
        listOfPossibleNumbers = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            listOfPossibleNumbers.add(i);
        }
    }

    SudokuElement(List<Integer> list, int value, int columnNumber, int rowNumber) {
        this.listOfPossibleNumbers = list;
        this.value = value;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    void setValue(int value) {
        this.value = value;
    }

    void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    int getValue() {
        return value;
    }

    int getColumnNumber() {
        return columnNumber;
    }

    int getRowNumber() {
        return rowNumber;
    }

    List<Integer> getListOfPossibleNumbers() {
        return listOfPossibleNumbers;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuElement that = (SudokuElement) o;
        return value == that.value &&
                columnNumber == that.columnNumber &&
                rowNumber == that.rowNumber &&
                Objects.equals(listOfPossibleNumbers, that.listOfPossibleNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, listOfPossibleNumbers, columnNumber, rowNumber);
    }
}
