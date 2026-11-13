package code;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;

public class Player implements Serializable {
    final long id;
    double x,y;
    int width, height;
    Color color;
    public Player(double x, double y, int width, int height, Color color) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.color = color;
        this.id = 1L;
    }
    public void draw(Canvas canvas) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
        bs.show();
        g.dispose();
    }
}
