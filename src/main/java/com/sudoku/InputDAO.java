package com.sudoku;

public class InputDAO {
    private int column;
    private int row;
    private int number;

    public InputDAO(int column, int row, int number) {
        this.column = column;
        this.row = row;
        this.number = number;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }
}
