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
        char key = e.getKeyChar();
        this.keys.add(key);
        System.out.println(keys);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        this.keys.remove(key);
        System.out.println(keys);
    }
    static void main() throws IOException {
        Client client = new Client("localhost", 8888);
        Player player = new Player(500, 200, 50, 50, Color.RED);

        new Thread(() -> {
            double drawTime = 1000000000 / client.FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            while (true) {
                ArrayList<Player> players = new ArrayList<>();
                try {
                    client.out.writeObject(new Player(player.x, player.y, player.width, player.height, player.color));
                    players = (ArrayList<Player>) client.in.readObject();
                    System.out.println(players);
                }
                catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawTime;
                lastTime = currentTime;
                if (delta >= 1) {
                    player.move(client.keys);
                    for (Player p : players) {
                        Player.draw(p, client.canvas);
//                        System.out.println(p);
                    }
//                    client.canvas.getGraphics().clearRect(0, 0, client.frameWidth, client.frameHeight);
                    delta--;
                }
            }
        }).start();
    }
}
