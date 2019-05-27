package com.sudoku;

class OnlyOptionDAO {
    private boolean onlyOption;
    private int value;

    OnlyOptionDAO(boolean onlyOption, int value) {
        this.onlyOption = onlyOption;
        this.value = value;
    }

    boolean isOnlyOption() {
        return onlyOption;
    }

    int getValue() {
        return value;
    }
}
