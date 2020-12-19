package sample;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class Grid {
    Cell cell = new Cell();

    // dimensions of screen
    private final int width = 1000;
    private final int height = 1000;
    private int genCount = 0;  // generations passed
    private int liveCount = 0;

    public void incGen() {genCount++;}
    public int getGen() {return genCount;}
    public void resetGen() {genCount = 0;}

    public void incLive() {liveCount++;}
    public int getLive() {return liveCount;}
    public void decLive() {liveCount--;}
    public void resetLive() {liveCount = 0;}

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    // 2d Arraylist storing location of cells
    private final ArrayList<ArrayList<Cell>> gridCells = new ArrayList<ArrayList<Cell>>(width/cell.getSize());

    public ArrayList<ArrayList<Cell>> getGrid() {
        return gridCells;
    }
    // creates the individual cells
    public Cell createCell(int x, int y, GridPane tile) {
        Cell cell = new Cell();  // instance of class cell
        cell.setCell(x, y);
        tile.add(cell.getCell(), y, x);
        return cell;
    }

    // creates an entire grid cells
    public void generateCells(int width, int height, GridPane tile) {
        width /= cell.getSize();
        height /= cell.getSize();

        for (int x = 0; x < width; x++){  // generates enough cells to fill scene
            ArrayList<Cell> cells = new ArrayList<Cell>();
            for (int y = 0; y < height; y++){
                cells.add(createCell(x, y, tile));
            }
            gridCells.add(cells);
        }
    }

}
