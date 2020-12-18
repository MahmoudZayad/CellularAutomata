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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

    int genCount = 0;  // counts generations
    ScheduledExecutorService exServ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(gridPane, width, height);  // create scene and generate cells
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.generateCells(width, height, gridPane);  // cell generation and scene set
        gridPane.setOnKeyPressed(this);
        clickDraw();
        dragDraw();
        setGameStatus();
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
                if(tempCell.equals(c)) {  // prevents from redrawing cell repeatedly
                    return;
                }
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
                if(tempCell.equals(c)) {  // prevents from redrawing cell repeatedly
                    return;
                }
                c.updateStatus();  // update cell and store temp data
                tempCell = c;
            }
        });
    }

    public void setGameStatus() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameStatus = !gameStatus;
                if(gameStatus) {
                    exServ =  Executors.newSingleThreadScheduledExecutor();
                    exServ.scheduleAtFixedRate(this::_nextGeneration, 0, 50, TimeUnit.MILLISECONDS);
                } else {
                    _pause();
                }
            }
        });
    }

    private void _gameLogic(int x, int y, ArrayList <Pair<Integer, Integer>> updated) {
        if (grid.getGrid().get(y).get(x).getStatus()) {  // if live cell
            rules.together(grid, x, y, updated);
        } else {  // if dead cell
            rules.lonely(grid, x, y, updated);
        }
    }

    // determines nextGeneration
    private void _nextGeneration(){
        Rules rules = new Rules();
        ArrayList <Pair<Integer, Integer>> updated = new ArrayList<>(); // stores cells that need update
        for (int x = 0; x < rules.getxCells(); x++){  // loops through all cells and determines status
            for (int y = 0; y < rules.getyCells(); y++){
                _gameLogic(x, y, updated);
            }
        }
        // update all cells for next generation
        for (Pair<Integer, Integer> p : updated) {
            Cell c = grid.getGrid().get(p.getKey()).get(p.getValue());
            c.updateStatus(); // update cell and store temp data
        }
        genCount++;
    }

    private void _pause() {
        try {
            if (!exServ.awaitTermination(20, TimeUnit.MILLISECONDS)) {
                exServ.shutdownNow();
            }
        } catch (InterruptedException e) {
            exServ.shutdownNow();
        }
    }


}
