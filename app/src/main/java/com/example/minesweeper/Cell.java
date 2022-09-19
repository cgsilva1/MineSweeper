package com.example.minesweeper;

public class Cell {
    public static final int BOMB = -1;
    public static final int BLANK = 0;

    private int index;
    private boolean isRevealed;
    private boolean isFlagged;

    public Cell(int index) {
        this.index = index;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
