package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    Grid grid = new Grid();
    private int width = 500;
    private int height = 500;

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        grid.makeGrid(width, height);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
