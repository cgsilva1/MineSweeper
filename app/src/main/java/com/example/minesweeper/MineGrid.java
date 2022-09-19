package com.example.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineGrid {

    private List<Cell> cells;
    private int size;
    private int col;

    public MineGrid(int size, int col) {
        this.size = size;
        this.col = col;
        this.cells = new ArrayList<>();
        for (int i = 0; i < size * size; i++) { //change to go by col
            cells.add(new Cell(Cell.BLANK));
        }
    }

    //generating grid with bombs randomly placed
    public void generateGrid(int totalBombs) {
        int bombsSet = 0;
        while (bombsSet < totalBombs) {
            int x = new Random().nextInt(size); //rand row
            int y = new Random().nextInt(size); //rand col

            if (cellLocation(x, y).getIndex() == Cell.BLANK) { //if cell that that index if blank, set bomb
                cells.set(x + (y*size), new Cell(Cell.BOMB));
                bombsSet++;
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (cellLocation(x, y).getIndex() != Cell.BOMB) {
                    int bombsNum = 0;
                    List<Cell> adjacentCells = adjacentCells(x, y);
                    for (Cell cell: adjacentCells) {
                        if (cell.getIndex() == Cell.BOMB) {
                            bombsNum += 1;
                        }
                    }
                    if (bombsNum > 0) {
                        cells.set(x + (y*size), new Cell(bombsNum));
                    }
                }
            }
        }
    }

    public Cell cellLocation(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return null;
        }
        return cells.get(x + (y*size)); //gets index of cell
    }

    public List<Cell> adjacentCells(int x, int y) {
        List<Cell> adjacentCells = new ArrayList<>();
        List<Cell> cellsList = new ArrayList<>();

        cellsList.add(cellLocation(x+1, y));
        cellsList.add(cellLocation(x-1, y));
        cellsList.add(cellLocation(x, y-1));
        cellsList.add(cellLocation(x, y+1));
        cellsList.add(cellLocation(x-1, y-1));
        cellsList.add(cellLocation(x+1, y+1));
        cellsList.add(cellLocation(x+1, y-1));
        cellsList.add(cellLocation(x-1, y+1));

        for (Cell cell: cellsList) {
            if (cell != null) {
                adjacentCells.add(cell);
            }
        }
        return adjacentCells;
    }

    public void showBombs() {
        for (Cell c: cells) {
            if (c.getIndex() == Cell.BOMB) {
                c.setRevealed(true);
            }
        }

    }

    public int[] setLoc(int index) {
        int y = index / size;
        int x = index - (y*size);
        return new int[]{x, y};
    }

    public List<Cell> getCells() {
        return cells;
    }
}
