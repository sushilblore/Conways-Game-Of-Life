package com.mygame.gameoflife.gamelogic;

import com.mygame.gameoflife.data.UserTouchEvents;
import com.mygame.gameoflife.gameoflife.GlobalContext;
import com.mygame.gameoflife.ui.Cell;
import com.mygame.gameoflife.ui.MyGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class GameLogic {

    private GlobalContext mContext;
    private volatile Map<Cell, Integer> neighborMap =
            new HashMap<Cell, Integer>();
    private volatile Collection<Cell> cells = new HashSet<Cell>(3000, 0.2f);

    public GameLogic(GlobalContext context) {
        mContext = context;
    }

    public void tick() {

        Set<UserTouchEvents> touches = mContext.getTouchInput()
                .getProcessed();
        for (UserTouchEvents touch : touches) {
            cells.add(new Cell(touch.x, touch.y));
        }

        compute();
    }

    private void compute() {
        long start = System.currentTimeMillis();

        Collection<Cell> cellsToDestroy = new HashSet<Cell>();
        Collection<Cell> cellsToCreate = new HashSet<Cell>();
        Collection<Cell> newCells = new HashSet<Cell>(cells);

        for (Cell cell : newCells) {

            int neighbors = countNeighbors(cell, newCells);

            if (neighbors < 2 || neighbors > 3) {
                cellsToDestroy.add(cell);
            }

            if (neighbors > 0) {
                collectNearbyRecreatedCandidates(cell, newCells, cellsToCreate);
            }
        }

        newCells.removeAll(cellsToDestroy);

        newCells.addAll(cellsToCreate);

        neighborMap = new HashMap<Cell, Integer>();

        cells = newCells;

        long end = System.currentTimeMillis();
        long delta = end - start;
    }

    private int countNeighbors(Cell cell, Collection<Cell> cells) {

        if (neighborMap.containsKey(cell)) {
            return neighborMap.get(cell);
        }

        int count = 0;

        int x = cell.getRow();
        int y = cell.getCol();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {

                if (isOutOfBounds(i, y, mContext.getGridView())) {
                    continue;
                }

                if (i == x && j == y) {
                    continue;
                }

                if (cells.contains(new Cell(i, j))) {
                    count++;
                }
            }
        }

        neighborMap.put(cell, count);

        return count;
    }


    private void collectNearbyRecreatedCandidates(Cell cell,
                                                     Collection<Cell> cells,
                                                     Collection<Cell> candidates) {

        int x = cell.getRow();
        int y = cell.getCol();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {

                if (isOutOfBounds(i, y, mContext.getGridView())) {
                    continue;
                }

                if (i == x && j == y) {
                    continue;
                }

                Cell c = new Cell(i, j);

                if (cells.contains(c) || candidates.contains(c)) {
                    continue;
                } else {
                    int neighbours = countNeighbors(c, cells);
                    if (neighbours == 3) {
                        candidates.add(c);
                    }
                }
            }
        }
    }

    public boolean isOutOfBounds(int x, int y, MyGridView gridview) {

        if (x < -1 || y < -1
                || x > gridview.getMatrixWidth()
                || y > gridview.getMatrixHeight()) {
            return true;
        }

        return false;
    }

    public List<Cell> getCells() {
         return new ArrayList<Cell>(cells);
    }

    public void resetGameLogic() {
        cells.clear();
    }
}
