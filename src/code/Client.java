package code;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    String host; int port;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    JFrame frame; Canvas canvas;
    int frameWidth; int frameHeight;
    int FPS;

    public Client(String host, int port) throws IOException {
        this.host = host; this.port = port; this.socket = new Socket(host, port);
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.FPS = 60;
        System.out.println("Connected");
    }
    public void createFrame(int width, int height) {
        this.frameWidth = width; this.frameHeight = height;
        frame = new JFrame();
        frame.setSize(width, height); frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); frame.setVisible(true);
        canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setFocusable(true);
        frame.add(canvas); frame.pack();
    }

    public Player listen() {
        while (true) {
            try {
                return (Player) in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void send(Player player) {
        try {
            out.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void main() throws IOException {
        Client client = new Client("localhost", 8888);
        client.createFrame(800, 600);
        Player player = new Player(100, 200, 50, 50, Color.RED);

        double drawTime = 1000000000/client.FPS; double delta = 0;
        long lastTime = System.nanoTime(); long currentTime;
        
        new Thread(() -> {
            Player p = client.listen();
            p.draw(client.canvas);
        }).start();
        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawTime;
            lastTime = currentTime;

            if (delta >= 1) {
                p.draw(client.canvas);
                client.canvas.getGraphics().clearRect(0, 0, client.frameWidth, client.frameHeight);
                delta--;
            }
            client.send(player);
        }
    }
}
