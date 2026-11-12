package code;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    String host; int port;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Client(String host, int port) throws IOException {
        this.host = host; this.port = port;
        this.socket = new Socket(host, port);
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Connected");
    }
    public void listen() {
        new Thread(() -> {
            while (true) {
                try {
                    Player p = (Player) in.readObject();
                    System.out.println(p.x);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void send(Player player) {
        new Thread(() -> {
            while (true) {
                try {
                    out.writeObject(player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main() throws IOException {
        Client client = new Client("localhost", 8888);
        Player player = new Player(100, 200, 50, 50);
        client.listen();
        client.send(player);
    }
}
