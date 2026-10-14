package code;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.awt.color.*;

public class Client{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String user;
    private Player player;
    private Player p;

    public Client(Socket socket, String user) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.user = user;
            this.player = new Player(150, 200, 50, 50, Color.RED, 10);
        }
        catch (IOException e) {
            closeAll(socket, in, out);
        }
    }
    public void sendMessage() throws IOException {
        while (socket.isConnected()) {
            out.writeObject(player);
            out.flush();
        }
    }
    public void listen() {
        new Thread(new Runnable() {
            public void run() {
                while (socket.isConnected()) {

                }
            }
        }).start();
    }
    public void closeAll(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
}
    public static void main() throws IOException {
        Socket socket = new Socket("localhost", 8888);
        Client client = new Client(socket, "j");
        client.listen();
        client.sendMessage();
    }
}
