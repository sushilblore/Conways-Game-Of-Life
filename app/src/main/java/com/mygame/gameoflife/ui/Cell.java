package com.mygame.gameoflife.ui;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class Cell {

    private final int row;
    private final int col;

    public Cell(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (row != other.row)
            return false;
        if (col != other.col)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cell [row=" + row + ", col=" + col + "]";
    }

}
