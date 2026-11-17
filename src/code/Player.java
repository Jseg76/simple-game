package code;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    final long id;
    double x,y, velX, velY;
    int width, height;
    Color color;
    public Player(double x, double y, int width, int height, Color color) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.color = color;
        this.id = 1L;
    }
    public void move(ArrayList<Character> keys) {
        for (char key : keys)
            switch (key) {
                case 'w' ->
                    this.velY = -10;
                case 's' ->
                    this.velY = 10;
                case 'a' ->
                    this.velX = -10;
                case 'd' ->
                    this.velX = 10;
        }
        this.x += this.velX;
        this.y += this.velY;
    }
    public static void draw(Player player, Canvas canvas) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(player.color);
//        System.out.println(player.x);
        g.fillRect((int) (player.x+.5), (int) (player.y+.5), player.width, player.height);
        bs.show();
        g.dispose();
    }
}
