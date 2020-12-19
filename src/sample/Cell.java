package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell{

    private boolean live = false;  // Cell's status

    private final int size = 100 ;
    private Rectangle cell;

    // update status
    public void updateStatus(Grid grid) {  // now also tracks amount of live cells
        if (live) {  // Determines if cell status and updates
            live = false;
            cell.setFill(Color.BLACK);
            grid.decLive();
        } else {
            live = true;
            cell.setFill(Color.WHITE);
            grid.incLive();
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
