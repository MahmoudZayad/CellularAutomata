package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    public GridPane gridPane = new GridPane();  //Entire scene
    public UserInterface userI = new UserInterface();
    public HBox hbox = userI.createButtons();
    public AnchorPane anchorPane = new AnchorPane();
    private Scene scene;

    private int _offset() {return 33;}  // offset of Grid from HBox

    private Cell cell = new Cell(); // temporary Cell
    private final Grid grid = new Grid();  // contains Cells
    private final Rules rules = new Rules();  // game of Life Rules

    private final int width = grid.getWidth();
    private final int height = grid.getHeight();

    private ScheduledExecutorService exServ;  // to execute simulation at fixed rate


    @Override
    public void start(Stage primaryStage) throws Exception {
        addAnchorPane();
        scene = new Scene(anchorPane, width, height + _offset());  // create scene and generate cells
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.generateCells(width, height, gridPane);  // cell generation and scene set
        gridPane.setOnKeyPressed(this);
        startControl();  // controls
        nextControl();
        clearControl();
        clickDraw();
        dragDraw();
        startKey();
        clearKey();
        nextKey();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent keyEvent){
    }

    // create anchorPane and set anchors
    private void addAnchorPane() {
        anchorPane.getChildren().addAll(gridPane, hbox);
        AnchorPane.setTopAnchor(gridPane, (double) _offset());
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setTopAnchor(hbox, 0.0);

    }

    // allows user to change status of cells by clicking on them
    private void clickDraw() {
        gridPane.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                _draw(mouseEvent); // draw Cell
            }
        });
    }

    // allows user to drag mouse to draw on cells
    private void dragDraw() {
        gridPane.setOnMouseDragged(mouseEvent-> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                _draw(mouseEvent);  // draw Cell
            }
        });
    }

    // updates cells when interacted with a mouseEvent
    private void _draw(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getSceneX()/cell.getSize();  // get cell location
        int y = (int) (mouseEvent.getSceneY() - _offset())/cell.getSize();
        if(y < 0 || y > rules.getyCells() - 1 ||
           x < 0 || x > rules.getxCells() - 1) {  // prevents accessing out of range indicies
            return;
        }
        Cell c = grid.getGrid().get(y).get(x);
        if(cell.equals(c)) {  // prevents from redrawing cell repeatedly
            return;
        }
        c.updateStatus(grid);  // update cell and store temp data
        cell = c;
        userI.liveText.setText("Alive Cells: " + String.valueOf(grid.getLive()));
    }

    // ~~~~~~~~~~~~~~~Key Shortcuts
    // allows user to start the simulation with S key
    private void startKey() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                userI.start.fire();  // fires start button
                event.consume();
            }
        });
    }

    // allows user to clear screen with C Key
    private void clearKey() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.C) {
                userI.clear.fire();  // fires clear button
                event.consume();
            }
        });
    }

    // allows user to step one generation with N Key
    private void nextKey() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.N) {
                userI.next.fire();  // fires next button
                event.consume();
            }
        });
    }
    //~~~~~~~~~~~~~~~~~~~~~~End of Key Shortcuts


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
            cell.updateStatus(grid); // update cell and store temp data
        }
        grid.incGen();
        userI.genText.setText("Generation: " + String.valueOf(grid.getGen()));
        userI.liveText.setText("Alive Cells: " + String.valueOf(grid.getLive()));
    }


    // ~~~~~~~~~~~~Button functions
    private void startControl() {   // allows simulation to start and stop
        userI.start.setOnAction(ActionEvent -> {
            if(userI.start.getText().equals("Start")) {
                _start();  // creates new thread and at a rate of getSpeed() simulates generation growth
            } else {
                _stop();  // ends thread and updates button
            }
        });
    }

    private void nextControl() { // steps forward one generation
        userI.next.setOnAction(ActionEvent -> {
            _nextGeneration();
        });
    }

    private void clearControl() {  // resets status of all cells
        userI.clear.setOnAction(ActionEvent -> {
            for (ArrayList<Cell> row : grid.getGrid()){  // goes through the 2d Array and kills cells
                for (Cell col : row) {
                    col.resetStatus();
                    grid.resetGen();
                    grid.resetLive();
                    userI.genText.setText("Generation: " + String.valueOf(grid.getGen()));
                    userI.liveText.setText("Alive Cells: " + String.valueOf(grid.getLive()));
                }
            }
        });
    }
    // ~~~~~~~~~~~~~~End of Button Functions


    // ~~~~~~~~~~~~~~~~Helper Functions for Controls
    private long _getSpeed() {  // determines rate of generation growth
        return (long) (180 - userI.speed.getValue());
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
            if (!exServ.awaitTermination(30, TimeUnit.MILLISECONDS)) {
                exServ.shutdownNow();
            }
        } catch (InterruptedException e) {
            exServ.shutdownNow();
        }
        userI.start.setText("Start");
    }


    // creates execution fixed rate thread and updates Start Button text
    private void _start() {
        exServ =  Executors.newSingleThreadScheduledExecutor();  // creates new thread at rate of getSpeed
        exServ.scheduleAtFixedRate(this::_nextGeneration, 0, _getSpeed(), TimeUnit.MILLISECONDS);
        userI.start.setText("Stop");
    }
    // ~~~~~~End of Helper Functions


}
