package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Client extends JFrame implements KeyListener {
    String host;
    int port, frameWidth, frameHeight, FPS;
    Socket socket;
    ObjectOutputStream out; ObjectInputStream in;
    ArrayList<Character> keys;
    GPanel panel;

    public Client(String host, int port) throws IOException {
        FPS = 60;
        keys = new ArrayList<>();
        this.panel = new GPanel();
        getContentPane().add(panel);
        addKeyListener(this);

        frameWidth = 800; frameHeight = 600;
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.host = host; this.port = port; socket = new Socket(host, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    public static class GPanel extends JPanel {
        ArrayList<Player> players = new ArrayList<>();
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Player p : players) {
                g.setColor(p.color);
                g.fillRect((int) (p.x + .5), (int) (p.y + .5), p.width, p.height);
            }
        }
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
        Client client = new Client("10.218.144.16", 8888);
        Player player = new Player(200, 200, 50, 50, Color.BLUE);

        new Thread(() -> {
            double drawTime = 1000000000.0 / client.FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            while (true) {
                try {
                    client.out.writeObject(player);
                    client.out.reset();
                    client.panel.players = (ArrayList<Player>) client.in.readObject();
                }
                catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawTime;
                lastTime = currentTime;
                if (delta >= 1) {
                    player.move(client.keys);
                    client.panel.repaint();
                    delta--;
                }
            }
        }).start();
    }
}
