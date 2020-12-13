package sample;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import java.util.ArrayList;

public class Grid {


    Cell cell = new Cell();

    // dimensions of screen
    private final int width = 500;
    private final int height = 500;

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    // 2d Arraylist storing location of cells
    private ArrayList<ArrayList<Cell>> gridCells = new ArrayList<ArrayList<Cell>>(width/25);

    public ArrayList<ArrayList<Cell>> getGrid() {
        return gridCells;
    }
    // creates the individual cells
    public Cell createCell(int x, int y, GridPane tile) {
        Cell cell = new Cell();  // instance of class cell
        cell.setX(x);
        cell.setY(y);
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
