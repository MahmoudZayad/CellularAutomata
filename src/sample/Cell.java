package sample;

public class Cell{

    private boolean live = false;  // Cell's status

    private int x;
    private int y;

    // update status
    public boolean UpdateStatus() {
        if (live) {  // Determines if cell status and updates
            live = false;
        } else {
            live = true;
        }
        return live;
    }

    public boolean getStatus() {
        return live;
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
