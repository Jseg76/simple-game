package code;

import java.awt.*;
import java.awt.event.*;

public class Player {
    int x, y, width, height, speed;
    Color color;
    int scale;
    boolean aDown, dDown, wDown, sDown;

    public Player(int x, int y, int width, int height, Color color, int speed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }
    public void draw(Graphics g) {
        scale = Main.scale;
        g.setColor(this.color);
        g.fillOval(this.x*scale, this.y*scale, this.width*scale, this.height*scale);
    }
    public void moveX(int xSpeed) {
        if (this.aDown) {
            this.x -= xSpeed;
        }
        if (this.dDown) {
            this.x += xSpeed;
        }
    }
    public void moveY(int ySpeed) {
        if (this.wDown) {
            this.y -= ySpeed;
        }
        if (this.sDown) {
            this.y += ySpeed;
        }
    }

    public void update() {
        moveX(this.speed);
        moveY(this.speed);
    }
}
