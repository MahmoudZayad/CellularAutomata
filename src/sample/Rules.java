package sample;

import java.util.ArrayList;

public class Rules {
    Grid grid = new Grid();
    private final Cell c = new Cell();
    private final int xCells = grid.getHeight()/c.getSize();
    private final int yCells = grid.getWidth()/c.getSize();

    public int getxCells() { return xCells; }
    public int getyCells() { return yCells; }

    // prevents negative and larger than grid numbers for x Values
    private int _acrossScreenX(int x, int i) {
        if(x + i < 0) {
            x = xCells; return x;  // moves to left side of screen
        } else if (x + i > xCells - 1){
            x = 0; return x;  // moves to right side of screen
        } else {
            return x + i;
        }
    }

    // prevents negative and larger than grid numbers for y Values
    private int _acrossScreenY(int y, int j) {
        if(y + j < 0) {
            y = yCells; return y;  // moves to bottom of screen
        } else if (y + j > xCells - 1){
            y = 0; return y;  // moves to top of screen
        } else {
            return y + j;
        }
    }

    // Any live cell with two or three live neighbours survives.
    public Cell together(Cell cell, Grid grid) {
        int liveCells = 0;
        int x, y;
        for (int i = -1; i < 2; i++) {
            x = _acrossScreenX(cell.getX(), i);  //validates x
            for (int j = -1; j < 2; j++) {
                y = _acrossScreenY(cell.getY(), j);  // validates y
                if(grid.getGrid().get(y).get(x).getStatus()) {  // checks status
                    liveCells++;
                }
            }
        }
        if (liveCells < 2 || liveCells > 3) {  // determine status
            cell.updateStatus();
        }
        return cell;
    }

    public Cell lonely(Cell cell, Grid grid) {
        int liveCells = 0;
        int x, y;
        for (int i = -1; i < 2; i++) {
            x = _acrossScreenX(cell.getX(), i);  //validates x
            for (int j = -1; j < 2; j++) {
                y = _acrossScreenY(cell.getY(), j);  // validates y
                if(grid.getGrid().get(y).get(x).getStatus()) {  // checks status
                    liveCells++;
                }
            }
        }
        if (liveCells == 3) {  // determine status
            cell.updateStatus();
        }
        return cell;
    }
}

