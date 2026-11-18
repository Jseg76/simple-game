package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Client extends JFrame implements KeyListener {
    String host;
    int port, frameWidth, frameHeight, FPS;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    Canvas canvas;
    ArrayList<Character> keys;

    public Client(String host, int port) throws IOException {
        frameWidth = 800; frameHeight = 600;
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas(); canvas.setSize(800,600);
        canvas.setFocusable(true); canvas.addKeyListener(this);
        add(canvas); setVisible(true);
        setLocationRelativeTo(null);

        this.host = host; this.port = port; socket = new Socket(host, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        FPS = 60;
        keys = new ArrayList();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        this.keys.add(e.getKeyChar());
    }
    @Override
    public void keyReleased(KeyEvent e) {
        this.keys.removeAll(Collections.singleton(e.getKeyChar()));
    }
    static void main() throws IOException {
        Client client = new Client("localhost", 8888);
        Player player = new Player(200, 200, 50, 50, Color.BLUE);

        new Thread(() -> {
            double drawTime = 1000000000 / client.FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            while (true) {
                ArrayList<Player> players = new ArrayList<>();
                try {
                    client.out.writeObject(player);
                    client.out.reset();
                    players = (ArrayList<Player>) client.in.readObject();
                }
                catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawTime;
                lastTime = currentTime;
                if (delta >= 1) {
                    client.canvas.getGraphics().clearRect(0, 0, client.frameWidth, client.frameHeight);
                    player.move(client.keys);
                    System.out.println(player.x);
                    client.draw(players);
//                    Player.draw();
                    delta--;
                }
            }
        }).start();
    }
    public void draw(ArrayList<Player> players) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, frameWidth, frameHeight);
        for (Player p : players) {
            p.draw(g);
        }
        bs.show();
        g.dispose();
    }
}
