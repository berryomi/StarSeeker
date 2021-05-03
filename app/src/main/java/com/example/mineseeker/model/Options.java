package com.example.mineseeker.model;

/* Class for storing options */
public class Options {
    private int numRow = 4; // default 4
    private int numCol = 6; // default 6
    private int numMines = 6; // default 6

    //singleton
    private static Options instance;

    private Options(){

    }

    public static Options getInstance(){
        if (instance == null){
            instance = new Options();
        }
        return instance;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }


}
