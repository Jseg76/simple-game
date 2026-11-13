package code;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main {
    ArrayList<ClientHandler> clients;
    int port;
    public Main(int port) {
        this.port = port;
        this.clients = new ArrayList();
    }
    public void runServer() throws IOException {
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (true) {
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + " has joined ");
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }
        catch (IOException e) {
        }
    }
    public void send(ClientHandler self) {
        for (ClientHandler client : this.clients) {
            if (client != self) {
            }
        }
    }

    static void main() throws IOException {
        Main game = new Main(8888);
        game.runServer();
    }
}

class ClientHandler implements Runnable {
    Socket socket; Main game;
    ObjectInputStream in; ObjectOutputStream out;

    public ClientHandler(Socket socket, Main game) {
        this.socket = socket;
        this.game = game;
    }
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    Player p = (Player) in.readObject();
                    out.writeObject(p);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e){
            try {
                socket.close();
            }
            catch (IOException e1) {
            }
            game.clients.remove(this);
            System.out.println("client has left");
        }
    }
}
