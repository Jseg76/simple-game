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

    Player player = new Player(100, 100, 50, 50, Color.RED, 5);

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
                player.wDown = true;
                break;
            case 'A':
                player.aDown = true;
                break;
            case 'D':
                player.dDown = true;
                break;
            case 'S':
                player.sDown = true;
                break;
            case 'F':
                if (!fullscreen) {
                    Main.scale = 2;
                    fullscreen = true;
                    Main.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                    Main.frame.setLocationRelativeTo(null);
                }
                else {
                    Main.scale = 1;
                    fullscreen = false;
                    Main.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2,Toolkit.getDefaultToolkit().getScreenSize().height/2);
                    Main.frame.setLocationRelativeTo(null);
                }
        }
    }
    public void keyReleased(KeyEvent e) {
        switch ((char) e.getKeyCode()) {
            case 'W':
                player.wDown = false;
                break;
            case 'A':
                player.aDown = false;
                break;
            case 'D':
                player.dDown = false;
                break;
            case 'S':
                player.sDown = false;
                break;
        }
    }
    public void run(){
        double drawTime = 1000000000/Main.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/drawTime;
            lastTime = currentTime;

            if (delta>=1) {
                update();
                draw();
                delta--;
            }
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
        player.update();
    }
}
