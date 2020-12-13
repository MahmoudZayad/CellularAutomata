package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;



public class Main extends Application implements EventHandler<KeyEvent> {
    public GridPane gridPane = new GridPane();
    private Scene scene;
    private final Cell cell = new Cell();
    private final Grid grid = new Grid();
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
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameStatus = true;
            }
        });
        // events
        clickDraw();
        dragDraw();
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
                grid.getGrid().get(y).get(x).updateStatus(); // update cell and store temp data
                tempCell = grid.getGrid().get(y).get(x);
            }
        });
    }

    // allows user to drag mouse to draw on cells
    public void dragDraw() {
        scene.setOnMouseDragged(mouseEvent-> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                int x = (int) mouseEvent.getSceneX()/cell.getSize();  // get cell location
                int y = (int) mouseEvent.getSceneY()/cell.getSize();
                if(tempCell.equals(grid.getGrid().get(y).get(x))) {  // prevents from redrawing cell repeatedly
                    return;
                }
                grid.getGrid().get(y).get(x).updateStatus();  // update cell and store temp data
                tempCell = grid.getGrid().get(y).get(x);
            }
        });
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
