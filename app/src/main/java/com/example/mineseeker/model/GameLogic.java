package com.example.mineseeker.model;

import java.util.Random;


/* Class for game playing logic */
public class GameLogic {
    private Options options = Options.getInstance();

    private int NUM_ROWS = options.getNumRow(); // default 4
    private int NUM_COLS = options.getNumCol(); // default 6
    private int NUM_MINES = options.getNumMines(); // default 6

    private int clickCount = 0;
    private int mineFound = 0;

    private Boolean starArray[][] = new Boolean[NUM_ROWS][NUM_COLS]; // knows which cell contains stars
    private Boolean isClicked[][] = new Boolean[NUM_ROWS][NUM_COLS]; // knows which cell is clicked by the user for scan
    private Boolean isRevealed[][] = new Boolean[NUM_ROWS][NUM_COLS]; // knows which star is revealed

    public int getClickCount(){
        return clickCount;
    }

    public void increaseClickCount() {
        clickCount++;
    }

    public int getMineFound() {
        return mineFound;
    }

    public void increaseMineFound() {
        mineFound++;
    }

    public Boolean[][] getStarArray() {
        return starArray;
    }

    public Boolean[][] getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(int row, int col, Boolean value) {
        isClicked[row][col] = value;
    }

    public Boolean[][] getIsRevealed() {
        return isRevealed;
    }

    public void setIsRevealed(int row, int col, Boolean value) {
        isRevealed[row][col] = value;
    }

    public void initialize() {
        generateRandomNumbers();
        initializeIsClicked();
        initializeIsRevealed();
    }

    private void generateRandomNumbers() {
        Random rand = new Random();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                starArray[row][col] = false;
            }
        }

        int mineCount = 0;
        while (mineCount < NUM_MINES) {
            int randRow = rand.nextInt(NUM_ROWS);
            int randCol = rand.nextInt(NUM_COLS);
            if (!starArray[randRow][randCol]) { //
                starArray[randRow][randCol] = true;
                mineCount++;
            }
            else{
                continue;
            }
        }
    }

    private void initializeIsClicked() {
        // initialized all cells as false (unrevealed)
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                isClicked[row][col] = false;
            }
        }
    }

    private void initializeIsRevealed() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                isRevealed[row][col] = false;
            }
        }
    }

    public int countHiddenStars(int row, int col) {
        int hiddenStar = 0;

        for (int i = 0; i < NUM_ROWS; i++) {
            if (starArray[i][col] == true) {
                if (i == row) { // do not count the cell itself
                    continue;
                }
                if (isRevealed[i][col] == true) {
                    continue;
                }
                hiddenStar++;
            }
        }
        for (int j = 0; j < NUM_COLS; j++) {
            if (starArray[row][j] == true) {
                if (j == col){ // do not count the cell itself
                    continue;
                }
                if (isRevealed[row][j] == true) {
                    continue;
                }
                hiddenStar++;
            }
        }
        return hiddenStar;
    }



}
