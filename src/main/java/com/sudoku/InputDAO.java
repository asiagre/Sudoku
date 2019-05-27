package com.sudoku;

class InputDAO {
    private int column;
    private int row;
    private int number;

    InputDAO(int column, int row, int number) {
        this.column = column;
        this.row = row;
        this.number = number;
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }

    int getNumber() {
        return number;
    }
}
