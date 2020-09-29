package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.security.Key;


public class Main extends Application {
    public TilePane tile = new TilePane();
    Grid grid = new Grid();
    Text text = new Text();

    private int width = grid.getWidth();
    private int height = grid.getHeight();

    private boolean gameStatus = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(new Scene(tile, width, height));
        primaryStage.show();
//        grid.generateCells(width, height, tile);
        tile.setOnKeyPressed(this);
        tile.getChildren().add(text);
        primaryStage.OnKeyPressed(key -> {
            if(KeyCode.SPACE.equals(KeyCode) {
                text.setText("NICE COCK");
            }
        });

    }
;
    public static void main(String[] args) {
        launch(args);
    }


}
