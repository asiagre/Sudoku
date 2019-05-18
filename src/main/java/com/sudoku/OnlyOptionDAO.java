package com.sudoku;

public class OnlyOptionDAO {
    private boolean onlyOption;
    private int value;

    public OnlyOptionDAO(boolean onlyOption, int value) {
        this.onlyOption = onlyOption;
        this.value = value;
    }

    public boolean isOnlyOption() {
        return onlyOption;
    }

    public int getValue() {
        return value;
    }
}
