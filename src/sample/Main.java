package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;


public class Main extends Application implements EventHandler<KeyEvent> {
    public GridPane gridPane = new GridPane();
    public UserInterface c = new UserInterface();
    public HBox hbox = c.createButtons();
    public AnchorPane anchorPane = new AnchorPane();

    private Scene scene;

    private Cell cell = new Cell(); // temporary Cell
    private final Grid grid = new Grid();
    private final Rules rules = new Rules();

    private final int width = grid.getWidth();
    private final int height = grid.getHeight();

    private ScheduledExecutorService exServ;
    private boolean gameStatus = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addAnchorPane();
        scene = new Scene(anchorPane, width, height);  // create scene and generate cells
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.generateCells(width, height, gridPane);  // cell generation and scene set
        gridPane.setOnKeyPressed(this);
        startControl();
        nextControl();
        clearControl();
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
    private void clickDraw() {
        gridPane.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                int x = (int) mouseEvent.getSceneX()/cell.getSize(); // get cell location
                int y = (int) mouseEvent.getSceneY()/cell.getSize();
                Cell c = grid.getGrid().get(y).get(x);
                if(cell.equals(c)) {  // prevents from redrawing cell repeatedly
                    return;
                }
                c.updateStatus(); // update cell and store temp data
                cell = c;
            }
        });
    }

    // allows user to drag mouse to draw on cells
    private void dragDraw() {
        gridPane.setOnMouseDragged(mouseEvent-> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                int x = (int) mouseEvent.getSceneX()/cell.getSize();  // get cell location
                int y = (int) mouseEvent.getSceneY()/cell.getSize();
                Cell c = grid.getGrid().get(y).get(x);
                if(cell.equals(c)) {  // prevents from redrawing cell repeatedly
                    return;
                }
                c.updateStatus();  // update cell and store temp data
                cell = c;
            }
        });
    }


    public void setGameStatus() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameStatus = !gameStatus;
            }
        });
    }

    private void addAnchorPane() {
        anchorPane.getChildren().addAll(gridPane, hbox);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
        AnchorPane.setTopAnchor(hbox, 0.0);
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
            Cell cell = grid.getGrid().get(p.getKey()).get(p.getValue());
            cell.updateStatus(); // update cell and store temp data
        }
        grid.incGen();
        c.text.setText("Generation: " + String.valueOf(grid.getGen()));
    }


    // ~~~~~~Button functions
    private void startControl() {   // allows simulation to start and stop
        c.start.setOnAction(ActionEvent -> {
            if(c.start.getText().equals("Start")) {
                _start();  // creates new thread and at a rate of getSpeed() simulates generation growth
            } else {
                _stop();  // ends thread and updates button
            }
        });
    }

    private void nextControl() { // steps forward one generation
        c.next.setOnAction(ActionEvent -> {
            _nextGeneration();
        });
    }

    private void clearControl() {  // resets status of all cells
        c.clear.setOnAction(ActionEvent -> {
            for (ArrayList<Cell> r : grid.getGrid()){
                for (Cell c : r) {
                    c.resetStatus();
                }
            }
        });
    }

    // ~~~~~~~~End of Button Functions


    // ~~~~~~~~~~Helper Functions for Controls
    private long _getSpeed() {  // determines rate of generation growth
        return (long) (110 - c.speed.getValue());
    };

    // Determines which rule set the cell should follow
    private void _gameLogic(int x, int y, ArrayList<Pair<Integer, Integer>> updated) {
        if (grid.getGrid().get(y).get(x).getStatus()) {  // if live cell
            rules.together(grid, x, y, updated);
        } else {  // if dead cell
            rules.lonely(grid, x, y, updated);
        }
    }


    // deletes thread of execution and updates startButton text
    private void _stop() {
        try {
            if (!exServ.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                exServ.shutdownNow();
            }
        } catch (InterruptedException e) {
            exServ.shutdownNow();
        }
        c.start.setText("Start");
    }


    // creates execution fixed rate thread and updates Start Button text
    private void _start() {
        exServ =  Executors.newSingleThreadScheduledExecutor();  // creates new thread at rate of getSpeed
        exServ.scheduleAtFixedRate(this::_nextGeneration, 0, _getSpeed(), TimeUnit.MILLISECONDS);
        c.start.setText("Stop");
    }
    // ~~~~~~End of Helper Functions




}
