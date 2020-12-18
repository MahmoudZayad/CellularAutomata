package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class Rules {
    private final Grid g = new Grid();
    private final Cell c = new Cell();
    private final int xCells = g.getHeight()/c.getSize();  // width in cells
    private final int yCells = g.getWidth()/c.getSize();  // length in cells

    public int getxCells() { return xCells; }
    public int getyCells() { return yCells; }


    // Any live cell with two or three live neighbours survives.
    public void together(Grid grid, int a, int b, ArrayList <Pair<Integer, Integer>> updated) {
        Pair <Integer, Integer> cell = new Pair<>(b,a);
        int liveCells = _liveCellCount(grid, a, b);
        if (liveCells < 2 || liveCells > 3) {  // determine status
            updated.add(cell);
        }
    }

    public void lonely(Grid grid, int a, int b, ArrayList <Pair<Integer, Integer>> updated) {
        Pair <Integer, Integer> cell = new Pair<>(b,a);
        if (_liveCellCount(grid, a, b) == 3) {  // determine status
            updated.add(cell);
        }
    }

    // prevents negative and larger than grid numbers for y Values
    private int _acrossScreen(int y, int j, int numCells) {
        if(y + j < 0) {
            y = numCells - 1; return y;  // moves to bottom of screen
        } else if (y + j > numCells - 1){
            y = 0; return y;  // moves to top of screen
        } else {
            return y + j;
        }
    }

    // counts surrounding live cells
    private int _liveCellCount(Grid grid, int a, int b) {
        int x, y;
        int liveCells = 0;
        for (int i = -1; i < 2; i++) {
            x = _acrossScreen(a, i, xCells);  //validates x
            for (int j = -1; j < 2; j++) {
                y = _acrossScreen(b, j, yCells);  // validates y
                if(a == x && b== y) {  // prevents the center cell being counted
                    continue;
                }
                if(grid.getGrid().get(y).get(x).getStatus()) {  // checks status
                    liveCells++;
                }
            }
        }
        return liveCells;
    }
}
