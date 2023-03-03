package sample;

import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.ParsePosition;

public class UserInterface implements EventHandler<ActionEvent> {
    Grid grid = new Grid();
    Button start = _startButton();
    Button next = _nextButton();
    Button clear = _clearButton();
    Slider speed = _speed();
    Text genText = _genText();
    Text liveText = _liveText();
    Label l = _speedLabel(speed);
    TextField size = _sizeField();


    // adds buttons to HBox object to be used in Main
    public HBox createButtons(){
        HBox hb = new HBox();
        hb.setPadding(new Insets(5, 29, 5, 25));  // set HBox settings
        hb.setSpacing(10);
        hb.setMinWidth(grid.getHeight());
        hb.setAlignment(Pos.CENTER);
        hb.setStyle("-fx-background-color: #272727;");

        hb.getChildren().addAll(start, next, clear, l, speed, genText, liveText);
        return hb;
    }

    // ~~~~~~~Buttons
    private Button _startButton() {
        Button b = new Button();
        b.setText("Start");
        b.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        b.setPrefSize(60, 10);
        b.setMaxHeight(15);
        return b;
    }

    private Button _nextButton() {
        Button b = new Button();
        b.setText("Next Generation");
        b.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        b.setPrefSize(135, 10);
        b.setMaxHeight(15);
        return b;
    }

    private Button _clearButton(){
        Button b = new Button();
        b.setText("Clear");
        b.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        b.setPrefSize(60, 10);
        b.setMaxHeight(15);
        return b;
    }

    private Text _genText() {
        Text text = new Text("Generation: 0");
        text.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        return text;
    }

    private Text _liveText() {
        Text text = new Text("Alive Cells: 0");
        text.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        return text;
    }

    // Creates slider object and sets it to 0 - 100
    private Slider _speed() {
        Slider slider = new Slider(0, 120, 0);
        slider.setBlockIncrement(10);
        return slider;
    }

    private Label _speedLabel(Slider s) {
        Label l = new Label();
        l.setLabelFor(s);
        l.setText("Rate of Life:");
        l.setFont(Font.font("Unispace", FontWeight.NORMAL, 12));
        l.setTextFill(Color.WHITE);
        return l;
    }

    // allows user to chance the size of the Cells
    private TextField _sizeField() {
        TextField t = new TextField();
        return t;
    }
    // ~~~~~~~~~End of Button Creation





    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
