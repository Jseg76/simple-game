package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable, KeyListener{
    Canvas canvas = new Canvas();
    boolean running = false, fullscreen = false;
    int width = Main.width;
    int height = Main.height;

    public BufferStrategy bs;
    public Graphics g;

    Player player = new Player(100, 100, 50, 50, Color.RED, 10);

    public JFrame addFrame() {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.addKeyListener( this);
        return frame;
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        switch ((char) e.getKeyCode()) {
            case 'W':
                player.moveY(-10);
                break;
            case 'A':
                player.moveX(-10);
                break;
            case 'D':
                player.moveX(10);
                break;
            case 'S':
                player.moveY(10);
                break;
            case 'F':
                if (!fullscreen) {
                    fullscreen = true;
                    Main.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                }
                else {
                    fullscreen = false;
                    Main.frame.setSize(800, 600);
                }
        }
    }
    public void keyReleased(KeyEvent e) {
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
