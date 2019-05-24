package com.sudoku;

import java.util.*;


public class SudokuGame {
    public static final String SUDOKU = "SUDOKU";
    private SudokuBoard sudokuBoard = new SudokuBoard();
//    private Logic logic;
    private boolean changedBoard;
    private Random generator = new Random();
    private boolean changed = false;
    private boolean goodGuess = false;
    private Deque<SudokuBoard> stack = new ArrayDeque<>();

    public void tour() {
        do {
            String input = UserInterface.takeNumber();
            if(input.length() == 5) {
                String[] values = input.split(",");
                int column = Integer.parseInt(values[0]) - 1;
                int row = Integer.parseInt(values[1]) - 1;
                int number = Integer.parseInt(values[2]);
                addNumberToBoard(new InputDAO(column, row, number));
                System.out.println(sudokuBoard);
            } else if(input.equalsIgnoreCase(SUDOKU)) {
                completeSudoku();
            }
        } while(!sudokuBoard.isFull());
    }

    public boolean resolveSudoku() {
        tour();
        boolean newGame = UserInterface.newGameInput();
        return newGame;
    }

    public void addNumberToBoard(InputDAO inputDAO) {
        SudokuElement sudokuElement = sudokuBoard.getBoard().get(inputDAO.getRow()).getSudokuElementsRow().get(inputDAO.getColumn());
        if(sudokuElement.getValue() == -1) {
            long onTheList = sudokuElement.getListOfPossibleNumbers().stream()
                    .filter(integer -> integer == inputDAO.getNumber())
                    .count();
            if(onTheList == 1) {
                sudokuElement.setValue(inputDAO.getNumber());
                removeFromLists(sudokuElement);
            } else {
                UserInterface.wrongNumber();
            }
        } else {
            UserInterface.alreadyTaken();
        }

    }
    public void completeSudoku() {
        int i = 0;
        do {
            do {
                changedBoard = false;
                changed = false;
                checkingSudokuRow();
                checkingSudokuColumn();
                checkingSudokuSector();
            } while(changed || changedBoard);
            try {
                stack.offerFirst(sudokuBoard.deepCopy());
            } catch(CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }
            guessValue();
            System.out.println(sudokuBoard);
        } while(!sudokuBoard.isFull());
    }



    public void changedElement() throws SudokuException {
        if(stack.size() > 0) {
            SudokuBoard takenBoard = stack.pollFirst();
            for(SudokuRow sudokuRow : takenBoard.getBoard()) {
                for(SudokuElement sudokuElement : sudokuRow.getSudokuElementsRow()) {
                    if (sudokuElement.getValue() != sudokuBoard.getBoard().get(sudokuElement.getRowNumber()).getSudokuElementsRow().get(sudokuElement.getColumnNumber()).getValue()) {
                        sudokuElement.getListOfPossibleNumbers().remove((Integer) sudokuBoard.getBoard().get(sudokuElement.getRowNumber()).getSudokuElementsRow().get(sudokuElement.getColumnNumber()).getValue());
                    }
                }
            }
            sudokuBoard = takenBoard;
            changedBoard = true;
        } else {
            System.out.println(sudokuBoard);
            throw new SudokuException("Exception - stack is empty.");
        }
    }

    public void checkingSudokuRow() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            List<SudokuElement> list = sudokuBoard.getBoard().get(i).getSudokuElementsRow();
            try {
                addNumber(list);
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
        if(!changedBoard && !sudokuBoard.isFull())  {
            for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
                SudokuElement sudokuElement = list.get(i);
                if (sudokuElement.getValue() == -1) {
                    if (sudokuElement.getListOfPossibleNumbers().size() == 0) {
                        System.out.println(sudokuElement.getRowNumber() + " " + sudokuElement.getColumnNumber());
                        changedElement();
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

    private OnlyOptionDAO onlyOption(SudokuElement sudokuElement, List<SudokuElement> list) {
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
                changedElement();
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
            }
            return new OnlyOptionDAO(false, 0);
        }
    }

    public void guessValue() {
        if(!sudokuBoard.isFull()) {
            int guessColumn;
            int guessRow;
            do {
                guessColumn = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
                guessRow = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
            } while (sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn).getValue() != -1);
            SudokuElement element = sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn);
            List<Integer> listOfValues = element.getListOfPossibleNumbers();
            if (listOfValues.size() > 0) {
                int guessIndex = generator.nextInt(listOfValues.size());
                int guessValue = listOfValues.get(guessIndex);
                addNumberToBoard(new InputDAO(guessColumn, guessRow, guessValue));
                removeFromLists(sudokuBoard.getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn));
            }
        }
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

}
