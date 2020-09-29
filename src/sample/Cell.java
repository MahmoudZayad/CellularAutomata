package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

public class Cell{

    private boolean live = false;  // Cell's status

    private int x;
    private int y;

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
        cell = new Rectangle(x, y, 10, 10);
        cell.setFill(Color.BLACK);
        return cell;
    }

    public Rectangle getCell() {
        return cell;
    }

    // set x and y of cell
    public int setX(int x) {
        this.x = x;
        return this.x;
    }
    public int setY(int y) {
        this.y = y;
        return this.y;
    }
    // get x and y
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
