package code;

import java.awt.*;
import java.awt.event.*;

public class Player {
    int x, y, width, height, speed;
    Color color;

    public Player(int x, int y, int width, int height, Color color, int speed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
    public void moveX(int xSpeed) {
        this.x += xSpeed;
    }
    public void update() {

    }
}