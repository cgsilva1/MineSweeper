package com.example.minesweeper;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MineSweeperGame {
    private MineGrid mineGrid;
    private boolean gameOver;
    private boolean flagMode;
    private boolean clearMode;
    private int flagCount;
    private int numberBombs;
    private boolean timeExpired;

    public MineSweeperGame(int size, int col, int numberBombs) {
        this.gameOver = false;
        this.flagMode = false;
        this.clearMode = true;
        this.timeExpired = false;
        this.flagCount = 0;
        this.numberBombs = numberBombs;
        mineGrid = new MineGrid(size,col);
        mineGrid.generateGrid(numberBombs);
    }

    public void handleCellClick(Cell cell) {
        if (!gameOver && !isWin() && !timeExpired && !cell.isRevealed()) {
            if (clearMode) {
                clear(cell);
            } else if (flagMode) {
                flag(cell);
            }
        }
    }

    public void clear(Cell cell) {
        int index = getMineGrid().getCells().indexOf(cell);
        getMineGrid().getCells().get(index).setRevealed(true);

        if (cell.getIndex() == Cell.BOMB) { //if click a cell with a bomb the game is over
            gameOver = true;
        } else if (cell.getIndex() == Cell.BLANK) { //if click on a cell and the cell has nothing check adjacents
            List<Cell> toClear = new ArrayList<>();
            List<Cell> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(cell);

            while (toCheckAdjacents.size() > 0) {
                Cell c = toCheckAdjacents.get(0);
                int cellIndex = getMineGrid().getCells().indexOf(c);
                int[] cellPos = getMineGrid().setLoc(cellIndex);
                for (Cell adjacent: getMineGrid().adjacentCells(cellPos[0], cellPos[1])) {
                    if (adjacent.getIndex() == Cell.BLANK) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent); //add the number to adjacent cells
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent);
                        }
                    }
                }
                toCheckAdjacents.remove(c);
                toClear.add(c);
            }

            for (Cell c: toClear) {
                c.setRevealed(true); //set the object cell to shwo that that cell has been clicked on
            }
        }
    }

    public void flag(Cell cell) {
        cell.setFlagged(!cell.isFlagged());
        int count = 0;
        for (Cell c: getMineGrid().getCells()) {
            if (c.isFlagged()) {
                count++;
            }
        }
        flagCount = count;
    }

    public boolean isWin() {
        int numbersUnrevealed = 0;
        for (Cell c: getMineGrid().getCells()) {
            if (c.getIndex() != Cell.BOMB && c.getIndex() != Cell.BLANK && !c.isRevealed()) {
                numbersUnrevealed++;
            }
        }

        if (numbersUnrevealed == 0) { //no more cells to turn over so game won
            return true;
        } else {
            return false;
        }
    }

    public void toggleMode(View view) {
        clearMode = !clearMode;
        flagMode = !flagMode;
        TextView tv = (TextView) view;
        if(flagMode){
            tv.setText(R.string.flag);
            flagMode = true;
        }
        else{
            tv.setText(R.string.pick);
            flagMode = false;
        }
    }

    public void outOfTime() {
        timeExpired = true;
    }

    public boolean isLoose() {
        return gameOver;
    }

    public MineGrid getMineGrid() {
        return mineGrid;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getNumberBombs() {
        return numberBombs;
    }
}
