package sample;


import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class Grid {

    TilePane tile = new TilePane();
    Stage primaryStage;

    private Main main = new Main();

    private Group gGroup = new Group();
    private Cell [][] arrayCells = new Cell[main.getWidth()/10][main.getHeight()/10];

    public Cell createCell(int x, int y) {
        Rectangle square = new Rectangle(x, y, 10, 10);
        Cell cell = new Cell();  // instance of class cell
        cell.setX(x);
        cell.setY(y);
        square.setFill(Color.WHITE);
        square.setFill(Color.BLACK);
        gGroup.getChildren().add(square);
        return cell;
    }

    public void generateCells(int width, int height) {
        for (int x = 0; x <= width; x += 10){  // generates enough cells to fill scene
            for (int y = 0; y <= height; y += 10){
                Cell cell =  new Cell();
                cell = createCell(x, y);
                arrayCells[x/10][y/10] = cell;
            }
        }
    }

    public void makeGrid(int width, int height) {
        generateCells(width, height);
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(new Scene(tile, width, height));
        primaryStage.show();
    }



}
