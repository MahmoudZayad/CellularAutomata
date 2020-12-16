package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class Rules {
    Grid g = new Grid();
    private final Cell c = new Cell();
    private final int xCells = g.getHeight()/c.getSize();
    private final int yCells = g.getWidth()/c.getSize();

    public int getxCells() { return xCells; }
    public int getyCells() { return yCells; }

    // prevents negative and larger than grid numbers for x Values
    private int _acrossScreenX(int x, int i) {
        if(x + i < 0) {
            x = xCells - 1; return x;  // moves to left side of screen
        } else if (x + i > xCells - 1){
            x = 0; return x;  // moves to right side of screen
        } else {
            return x + i;
        }
    }

    // prevents negative and larger than grid numbers for y Values
    private int _acrossScreenY(int y, int j) {
        if(y + j < 0) {
            y = yCells - 1; return y;  // moves to bottom of screen
        } else if (y + j > xCells - 1){
            y = 0; return y;  // moves to top of screen
        } else {
            return y + j;
        }
    }

    // Any live cell with two or three live neighbours survives.
    public void together(Grid grid, int a, int b, ArrayList <Pair<Integer, Integer>> updated) {
        Pair <Integer, Integer> cell = new Pair<>(b,a);
        int liveCells = 0;
        for (int i = -1; i < 2; i++) {
            int x = _acrossScreenX(a, i);  //validates x
            for (int j = -1; j < 2; j++) {
                int y = _acrossScreenY(b, j);  // validates y
                System.out.println(x + "-" + y);
                if(grid.getGrid().get(y).get(x).getStatus()) {  // checks status
                    liveCells++;
                    System.out.println(liveCells + "live cell");
                }
            }
            System.out.println("next");
        }
        System.out.println(liveCells + "TOTAL live cell");
        if (liveCells-1 < 2 || liveCells-1 > 3) {  // determine status
            System.out.println(liveCells + "KILL LIVE CELL");
            updated.add(cell);
        }
    }

    public void lonely(Grid grid, int a, int b, ArrayList <Pair<Integer, Integer>> updated) {
        Pair <Integer, Integer> cell = new Pair<>(b,a);
        int liveCells = 0;
        int x, y;
        for (int i = -1; i < 2; i++) {
            x = _acrossScreenX(a, i);  //validates x
            for (int j = -1; j < 2; j++) {
                y = _acrossScreenY(b, j);  // validates y
//                System.out.println(x + "-" + y);
                if(grid.getGrid().get(y).get(x).getStatus()) {  // checks status
                    liveCells++;
                }
            }
        }
        if (liveCells == 3) {  // determine status
            System.out.println(liveCells + "MAKE LIVE CELL");
            updated.add(cell);
        }
    }
}

