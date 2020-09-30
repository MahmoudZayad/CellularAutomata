package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.security.Key;


public class Main extends Application implements EventHandler<KeyEvent> {
    public TilePane tile = new TilePane();
    Cell cell = new Cell();
    Grid grid = new Grid();

    private int width = grid.getWidth();
    private int height = grid.getHeight();

    private boolean gameStatus = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(tile, width, height);
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.generateCells(width, height, tile);
        tile.setOnKeyPressed(this);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameStatus = true;
            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                int x = ((int) mouseEvent.getSceneX()/cell.getSize());
                int y = ((int) mouseEvent.getSceneY()/cell.getSize());
                System.out.println(x + "-" + y);
                grid.getGrid().get(y).get(x).updateStatus();
            }
        });

    }
;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}
