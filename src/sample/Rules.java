package sample;

import java.util.ArrayList;

public class Rules {

    // Any live cell with two or three live neighbours survives.
    public void together(ArrayList<ArrayList<Cell>> gridCells, Cell cell) {
        int liveCells = 0;
        int x = cell.getX();
        int y = cell.getY();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if(gridCells.get(i + x).get(j + y).getStatus()) {
                    liveCells++;
                }
            }
        }

        if (liveCells < 2 || liveCells > 3) {
            cell.updateStatus();
        }
    }
}
