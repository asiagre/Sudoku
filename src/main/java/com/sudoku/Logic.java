package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    private SudokuGame sudokuGame;
    private SudokuBoard sudokuBoard;
    private Random generator = new Random();
    private boolean changed = false;


    public Logic(SudokuGame sudokuGame) {
        this.sudokuGame = sudokuGame;
        sudokuBoard = this.sudokuGame.getSudokuBoard();
    }

    public void checkingSudokuRow() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            List<SudokuElement> list = sudokuBoard.getBoard().get(i).getSudokuElementsRow();
            try {
                addNumber(list);
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
                System.out.println(sudokuBoard);
            }
        }
    }

    public void checkingSudokuColumn() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            List<SudokuElement> list = new ArrayList<>();
            for(int j = SudokuBoard.MIN_INDEX; j <= SudokuBoard.MAX_INDEX; j++) {
                list.add(sudokuBoard.getBoard().get(j).getSudokuElementsRow().get(i));
            }
            try {
                addNumber(list);
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
                System.out.println(sudokuBoard);
            }
        }
    }

    public void checkingSudokuSector() {
        List<SudokuElement> list = new ArrayList<>();
        int m = 0;
        int n = 0;
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            for(int j = SudokuBoard.MIN_INDEX; j <= SudokuBoard.MAX_INDEX; j++) {
                for(int k = SudokuBoard.MIN_INDEX; k <= SudokuBoard.MAX_INDEX; k++) {
                    if((j / 3) == m && (k / 3) == n) {
                        list.add(sudokuBoard.getBoard().get(j).getSudokuElementsRow().get(k));
                    }
                }
            }
            try {
                addNumber(list);
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
                System.out.println(sudokuBoard);
            }
            if(m < 2) {
                m++;
            } else {
                m = 0;
                n++;
            }
        }
    }

    public void addNumber(List<SudokuElement> list) throws SudokuException {
        if(!sudokuGame.isChangedBoard())  {
            for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
                SudokuElement sudokuElement = list.get(i);
                if (sudokuElement.getValue() == -1) {
                    if (sudokuElement.getListOfPossibleNumbers().size() == 0) {
                        sudokuGame.changedElement();
                    } else if (sudokuElement.getListOfPossibleNumbers().size() == 1) {
                        sudokuElement.setValue(sudokuElement.getListOfPossibleNumbers().get(0));
                        changed = true;
                        removeFromLists(sudokuElement);
                    } else {
                        OnlyOptionDAO onlyOptionDAO = onlyOption(sudokuElement, list);
                        if (onlyOptionDAO.isOnlyOption()) {
                            sudokuElement.setValue(onlyOptionDAO.getValue());
                            changed = true;
                            removeFromLists(sudokuElement);
                        }
                    }
                }
            }
        }
    }

    private OnlyOptionDAO onlyOption(SudokuElement sudokuElement, List<SudokuElement> list) throws SudokuException {
        List<Integer> copyList = new ArrayList<>(sudokuElement.getListOfPossibleNumbers());
        for(Integer value : sudokuElement.getListOfPossibleNumbers()) {
            for(SudokuElement element : list) {
                if(value == element.getValue()) {
                    copyList.remove(value);
                }
                for(Integer value2 : element.getListOfPossibleNumbers()) {
                    if(value.equals(value2)) {
                        copyList.remove(value);
                    }
                }
            }
        }
        if(copyList.size() == 0) {
            return new OnlyOptionDAO(false, 0);
        } else if(copyList.size() == 1) {
            return new OnlyOptionDAO(true, copyList.get(0));
        } else {
            try {
                sudokuGame.changedElement();
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
                System.out.println(sudokuBoard);
            }
            return new OnlyOptionDAO(false, 0);
        }
    }

    public void guessValue() {
        int guessColumn;
        int guessRow;
        do {
            guessColumn = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
            guessRow = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
        } while(sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn).getValue() != -1);
        SudokuElement element = sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn);
        List<Integer> listOfValues = element.getListOfPossibleNumbers();
        if(listOfValues.size() > 0) {
            int guessIndex = generator.nextInt(listOfValues.size());
            int guessValue = listOfValues.get(guessIndex);
            sudokuGame.addNumber(new InputDAO(guessColumn, guessRow, guessValue));
            removeFromLists(sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn));
        } //else {
//            try {
//                sudokuGame.changedElement();
//            } catch (SudokuException e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }

    public void removeFromLists(SudokuElement sudokuElement) {
        int valueOfSudokuElement = sudokuElement.getValue();
        int column = sudokuElement.getColumnNumber();
        int row = sudokuElement.getRowNumber();
        for(SudokuRow sudokuRow : sudokuBoard.getBoard()) {
            for(SudokuElement element : sudokuRow.getSudokuElementsRow()) {
                if(element.getColumnNumber() == column || element.getRowNumber() == row || ((element.getRowNumber() / 3 == row / 3) && (element.getColumnNumber() / 3 == column / 3))) {
                    element.getListOfPossibleNumbers().removeIf(integer -> integer == valueOfSudokuElement);
                }
            }
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
