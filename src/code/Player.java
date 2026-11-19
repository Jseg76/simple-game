package code;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    final long id;
    double x,y, velX, velY, friction;
    int width, height;
    Color color;
    public Player(double x, double y, int width, int height, Color color) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.color = color;
        this.id = 1L;
        this.friction = 0.3;
    }
    public void move(ArrayList<Character> keys) {
        if (keys.contains('w')) {this.velY = -10;}
        if (keys.contains('s')) {this.velY = 10;}
        if (keys.contains('a')) {this.velX = -10;}
        if (keys.contains('d')) {this.velX = 10;}
        if (!(keys.contains('a') || keys.contains('d'))) {this.velX *= this.friction;}
        if (!(keys.contains('w') || keys.contains('s'))) {this.velY *= this.friction;}
        this.x += this.velX;
        this.y += this.velY;
    }
}
