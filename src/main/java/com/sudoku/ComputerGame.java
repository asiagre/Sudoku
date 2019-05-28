package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ComputerGame {
    private SudokuGame sudokuGame;
    private Random generator = new Random();

    ComputerGame(SudokuGame sudokuGame) {
        this.sudokuGame = sudokuGame;
    }

    void fillSudokuWithPossibleNumbers() {
        checkingSudokuRow();
        checkingSudokuColumn();
        checkingSudokuSector();
    }

    void checkingSudokuRow() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            List<SudokuElement> list = sudokuGame.getSudokuBoard().getBoard().get(i).getSudokuElementsRow();
            try {
                computerAddNumber(list);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void checkingSudokuColumn() {
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            List<SudokuElement> list = new ArrayList<>();
            for(int j = SudokuBoard.MIN_INDEX; j <= SudokuBoard.MAX_INDEX; j++) {
                list.add(sudokuGame.getSudokuBoard().getBoard().get(j).getSudokuElementsRow().get(i));
            }
            try {
                computerAddNumber(list);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void checkingSudokuSector() {
        List<SudokuElement> list = new ArrayList<>();
        int sectorX = 0;
        int sectorY = 0;
        for(int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
            for(int j = SudokuBoard.MIN_INDEX; j <= SudokuBoard.MAX_INDEX; j++) {
                for(int k = SudokuBoard.MIN_INDEX; k <= SudokuBoard.MAX_INDEX; k++) {
                    if((j / 3) == sectorX && (k / 3) == sectorY) {
                        list.add(sudokuGame.getSudokuBoard().getBoard().get(j).getSudokuElementsRow().get(k));
                    }
                }
            }
            try {
                computerAddNumber(list);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if(sectorX < 2) {
                sectorX++;
            } else {
                sectorX = 0;
                sectorY++;
            }
        }
    }

    private void computerAddNumber(List<SudokuElement> list) throws SudokuException {
        if (!(sudokuGame.isChangedBoard() || sudokuGame.getSudokuBoard().isFull())) {
            for (int i = SudokuBoard.MIN_INDEX; i <= SudokuBoard.MAX_INDEX; i++) {
                SudokuElement sudokuElement = list.get(i);
                if (sudokuElement.getValue() == -1) {
                    if (sudokuElement.getListOfPossibleNumbers().size() == 0) {
                        sudokuGame.changeBoard();
                    } else if (sudokuElement.getListOfPossibleNumbers().size() == 1) {
                        sudokuGame.addNumber(new InputDAO(sudokuElement.getColumnNumber(), sudokuElement.getRowNumber(), sudokuElement.getListOfPossibleNumbers().get(0)));
                        sudokuGame.setChanged(true);
                    } else {
                        OnlyOptionDAO onlyOptionDAO = onlyOption(sudokuElement, list);
                        if (onlyOptionDAO.isOnlyOption()) {
                            sudokuGame.addNumber(new InputDAO(sudokuElement.getColumnNumber(), sudokuElement.getRowNumber(), onlyOptionDAO.getValue()));
                            sudokuGame.setChanged(true);
                        }
                    }
                }
            }
        }
    }

    private OnlyOptionDAO onlyOption(SudokuElement sudokuElement, List<SudokuElement> list) {
        List<Integer> copyList = new ArrayList<>(sudokuElement.getListOfPossibleNumbers());
        for(Integer value : sudokuElement.getListOfPossibleNumbers()) {
            list.stream()
                    .filter(element -> !(element.equals(sudokuElement)))
                    .flatMap(element -> element.getListOfPossibleNumbers().stream())
                    .filter(valueFromPossibleNumbers -> valueFromPossibleNumbers == value)
                    .forEach(filtredValue -> copyList.remove(filtredValue));
        }
        if(copyList.size() == 0) {
            return new OnlyOptionDAO(false, 0);
        } else if(copyList.size() == 1) {
            return new OnlyOptionDAO(true, copyList.get(0));
        } else {
            try {
                sudokuGame.changeBoard();
            } catch (SudokuException e) {
                System.out.println(e.getMessage());
            }
            return new OnlyOptionDAO(false, 0);
        }
    }

    void guessValue() {
        if(!sudokuGame.getSudokuBoard().isFull()) {
            int guessColumn;
            int guessRow;
            do {
                guessColumn = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
                guessRow = generator.nextInt(SudokuBoard.MAX_INDEX + 1);
            } while (sudokuGame.getSudokuBoard().getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn).getValue() != -1);
            SudokuElement element = sudokuGame.getSudokuBoard().getBoard().get(guessRow).getSudokuElementsRow().get(guessColumn);
            List<Integer> listOfValues = element.getListOfPossibleNumbers();
            if (listOfValues.size() > 0) {
                int guessIndex = generator.nextInt(listOfValues.size());
                int guessValue = listOfValues.get(guessIndex);
                sudokuGame.addNumber(new InputDAO(guessColumn, guessRow, guessValue));
            }
        }
    }
}
