package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

public class Cell{

    private boolean live = false;  // Cell's status

    private final int size = 25 ;
    private Rectangle cell;

    // update status
    public boolean updateStatus() {
        if (live) {  // Determines if cell status and updates
            live = false;
            cell.setFill(Color.BLACK);
        } else {
            live = true;
            cell.setFill(Color.WHITE);
        }
        return live;
    }

    public boolean getStatus() {
        return live;
    }

    public Rectangle setCell(int x, int y) {
        cell = new Rectangle(x, y, size, size);
        cell.setFill(Color.BLACK);
        return cell;
    }

    public Rectangle getCell() { return cell; }

    public int getSize() { return size; }


}
