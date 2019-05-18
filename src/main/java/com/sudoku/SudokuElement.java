package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SudokuElement {
    public static final int EMPTY = -1;
    private int value = EMPTY;
    private List<Integer> listOfPossibleNumbers = new ArrayList<>();
    private int columnNumber;
    private int rowNumber;

    public SudokuElement(int columnNumber) {
        this.columnNumber = columnNumber;
        for(int i = 1; i < 10; i++) {
            listOfPossibleNumbers.add(i);
        }
    }

    public SudokuElement(List<Integer> list, int columnNumber, int rowNumber) {
        this.listOfPossibleNumbers = list;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    public int getValue() {
        return value;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public List<Integer> getListOfPossibleNumbers() {
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
