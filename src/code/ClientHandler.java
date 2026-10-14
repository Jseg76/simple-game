package code;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String user = "j";
    private Player player;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.player = new Player(150, 200, 50, 50,Color.RED, 10);
            clients.add(this);
        }
        catch (IOException e) {
            closeAll(socket, in, out);
        }
    }
    public void run() {
        while (socket.isConnected()) {
//            System.out.println(out);
            sendMessage(out);
        }
    }
    public void sendMessage(Object message) {
        for (ClientHandler client : clients) {
            try {
                if (!client.user.equals(user)) {
                    client.out.writeObject(message);
                    client.out.flush();
                }
            }
            catch (IOException e) {
                closeAll(socket, in, out);
            }
        }
    }
    public void removeClient() {
        clients.remove(this);
    }
    public void closeAll(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        removeClient();
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
}
