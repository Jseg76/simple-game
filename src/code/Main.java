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
    ArrayList<Player> players;
    int port;
    ServerSocket serverSocket;
    public Main(int port) throws IOException {
        this.port = port;
        this.clients = new ArrayList();
        this.players = new ArrayList();
        this.serverSocket = new ServerSocket(this.port);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket, this);
            clients.add(clientHandler);
            this.players.add(new Player(0,0,1,1,Color.white));
            new Thread(clientHandler).start();
        }
    }
    public void sendAll() throws IOException {
        for (ClientHandler clientHandler : clients) {
            clientHandler.out.writeObject(this.players);
        }
    }
    public void main() throws IOException {
        Main game = new Main(8888);
        while (true) {
            this.sendAll();
        }
    }
}
class ClientHandler implements Runnable {
    Socket socket; Main game;
    ObjectInputStream in; ObjectOutputStream out;
    int clientNum;

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
                this.clientNum = game.clients.indexOf(this);
                ArrayList<Player> players = new ArrayList<>();
                try {
                    Player player = (Player) in.readObject();
                    game.players.set(clientNum, new Player(player.x, player.y, player.width, player.height, player.color));
                    players = game.players;
                    out.writeObject(players);
                }
                catch (ClassNotFoundException e) {throw new RuntimeException(e);
                }
            }
        } catch (IOException e){
            try {
                socket.close();
            }
            catch (IOException e1) {}
            game.clients.remove(this);
            System.out.println("client has left");
        }
    }
}
