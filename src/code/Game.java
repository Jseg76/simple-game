package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    Canvas canvas = Main.getCanvas();

    boolean running = false;
    int width = Main.width;
    int height = Main.height;

    public BufferStrategy bs;
    public Graphics g;

    Player player = new Player(100, 100, 50, 50, Color.RED, 10);

    public void addFrame() {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
    }
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (key == KeyEvent.VK_D) {
            player.moveX(player.speed);
        }
        if (key == KeyEvent.VK_A) {
            player.moveX(-player.speed);
        }
    }
    public void run(){
        while (running) {
            update();
            draw();
        }
    }
    public void draw(){
        bs = canvas.getBufferStrategy();
        if (bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        player.draw(g);
        bs.show();
        g.dispose();
    }
    public void update(){

    }
}
