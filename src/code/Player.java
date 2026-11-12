package code;

import java.io.Serializable;

public class Player implements Serializable {
    final long id;
    double x,y;
    int width, height;
    public Player(double x, double y, int width, int height) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.id = 1L;
    }
}
