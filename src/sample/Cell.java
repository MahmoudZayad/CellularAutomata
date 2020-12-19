package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell{

    private boolean live = false;  // Cell's status

    private final int size = 5 ;
    private Rectangle cell;

    // update status
    public void updateStatus() {
        if (live) {  // Determines if cell status and updates
            live = false;
            cell.setFill(Color.BLACK);
        } else {
            live = true;
            cell.setFill(Color.WHITE);
        }
    }

    public boolean getStatus() {
        return live;
    }
    public void resetStatus() {live = false; cell.setFill(Color.BLACK);}

    public void setCell(int x, int y) {
        cell = new Rectangle(x, y, size, size);
        cell.setFill(Color.BLACK);
    }

    public Rectangle getCell() { return cell; }

    public int getSize() { return size; }


}
