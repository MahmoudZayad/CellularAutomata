package sample;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UserInterface implements EventHandler<ActionEvent> {

    Button start = _startButton();
    Button next = _nextButton();
    Button clear = _clearButton();
    Slider speed = _speed();
    Text text = _genText();

    // adds buttons to HBox object to be used in Main
    public HBox createButtons(){
        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 7, 10, 7));  // set HBox settings
        hb.setSpacing(10);
        hb.setStyle("-fx-background-color: #272727;");

        hb.getChildren().addAll(start, next, speed, clear, text);
        return hb;
    }

    // ~~~~~~~Buttons
    private Button _startButton() {
        Button b = new Button();
        b.setText("Start");
        b.setPrefSize(50, 15);
        return b;
    }

    private Button _nextButton() {
        Button b = new Button();
        b.setText("Next Generation");
        b.setPrefSize(110, 15);
        return b;
    }

    private Text _genText() {
        Text text = new Text("Generation: 0");
        text.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.WHITE);
        return text;
    }

    // Creates slider object and sets it to 0 - 100
    private Slider _speed() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(10);
        return slider;
    }

    private Button _clearButton(){
        Button b = new Button();
        b.setText("Clear");
        b.setPrefSize(50, 15);
        return b;
    }
    // ~~~~~~~~~End of Button Creation





    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
