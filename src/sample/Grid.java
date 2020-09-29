package sample;

import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Grid {

    // dimensions of screen
    private int width = 500;
    private int height = 500;

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    // 2d Arraylist storing location of cells
    private ArrayList<ArrayList<Cell>> gridCells = new ArrayList<ArrayList<Cell>>(width/10);

    // creates the individual cells
    public Cell createCell(int x, int y, TilePane tile) {
        Cell cell = new Cell();  // instance of class cell
        cell.setX(x);
        cell.setY(y);
        cell.setCell(x, y);
        tile.getChildren().add(cell.getCell());
        return cell;
    }

    // creates an entire grid cells
    public void generateCells(int width, int height, TilePane tile) {
        for (int x = 0; x <= width; x += 10){  // generates enough cells to fill scene
            ArrayList<Cell> cells = new ArrayList<Cell>();
            for (int y = 0; y <= height; y += 10){
                cells.add(createCell(x, y, tile));
            }
            gridCells.add(cells);
        }
    }

}
