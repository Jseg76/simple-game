package code;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main {
    static ArrayList<ClientHandler> clients = new ArrayList();
    int port;
    public Main(int port) {
        this.port = port;
    }
    public void runServer() {
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (true) {
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + " has joined ");
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }
        catch (IOException e) {
            try {
                socket.close();
                clients.remove(this);
                System.out.println("client has left");
            }
            catch (IOException e1) {
                System.out.println("no");
            }
        }
    }
    public void send(ClientHandler self) {
        for (ClientHandler client : this.clients) {
            if (client != self) {
            }
        }
    }

    public static void main() throws IOException {
        Main game = new Main(8888);
        game.runServer();
    }
}

class ClientHandler implements Runnable {
    Socket socket;
    ObjectInputStream in; ObjectOutputStream out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            try {
                out.writeObject(in.readObject());
                System.out.println("working");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
