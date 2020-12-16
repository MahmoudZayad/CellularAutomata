package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;


public class Main extends Application implements EventHandler<KeyEvent> {
    public GridPane gridPane = new GridPane();
    private Scene scene;
    private final Cell cell = new Cell();
    private final Grid grid = new Grid();
    private final Rules rules = new Rules();
    private Cell tempCell = new Cell(); // for drawing


    private final int width = grid.getWidth();
    private final int height = grid.getHeight();

    private boolean gameStatus = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(gridPane, width, height);  // create scene and generate cells
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.generateCells(width, height, gridPane);  // cell generation and scene set
        gridPane.setOnKeyPressed(this);
        System.out.println(gridPane.getChildren());
        clickDraw();
        dragDraw();
        setGameStatus(primaryStage);
    }
;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent keyEvent){
    }

    // allows user to change status of cells by clicking on them
    public void clickDraw() {
        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                int x = (int) mouseEvent.getSceneX()/cell.getSize(); // get cell location
                int y = (int) mouseEvent.getSceneY()/cell.getSize();
                Cell c = grid.getGrid().get(y).get(x);

//                System.out.println(x +"-" + y); // debug
                c.updateStatus(); // update cell and store temp data
                tempCell = c;
            }
        });
    }

    // allows user to drag mouse to draw on cells
    public void dragDraw() {
        scene.setOnMouseDragged(mouseEvent-> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                int x = (int) mouseEvent.getSceneX()/cell.getSize();  // get cell location
                int y = (int) mouseEvent.getSceneY()/cell.getSize();
                Cell c = grid.getGrid().get(y).get(x);

//                System.out.println(x +"-" + y); // debug
                if(tempCell.equals(c)) {  // prevents from redrawing cell repeatedly
                    return;
                }
                c.updateStatus();  // update cell and store temp data
                tempCell = c;
            }
        });
    }

    public void setGameStatus(Stage primaryStage) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameStatus = !gameStatus;
                while(gameStatus) {
                    ArrayList <Pair<Integer, Integer>> updated = new ArrayList<>();
                    System.out.println(updated);
                    for (int x = 0; x < rules.getxCells(); x++){  // generates enough cells to fill scene
                        for (int y = 0; y < rules.getyCells(); y++){
                            gameLogic(x, y, updated);
                        }
                    }
                    // update all cells for next generation
                    for (Pair<Integer, Integer> p : updated) {
                        Cell c = grid.getGrid().get(p.getKey()).get(p.getValue());
                        c.updateStatus(); // update cell and store temp data
                    }
                    System.out.println(updated);
                    //update
                    System.out.println("UPDATE");
                    gameStatus = false;
                }
            }
        });
    }
    public void gameLogic(int x, int y, ArrayList <Pair<Integer, Integer>> updated) {
        if (grid.getGrid().get(y).get(x).getStatus()) {  // if live cell
            rules.together(grid, x, y, updated);
        } else {  // if dead cell
            rules.lonely(grid, x, y, updated);
        }
    }

    // creates a temp cell for later use maybe
    public Cell setTempCell(int x, int y) {
        Cell tCell = new Cell();
        tCell.setX(x);
        tCell.setY(y);
        tCell.setCell(x, y);
        return tCell;
    }

}
