package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    int port;
    String host;
    ArrayList <PrintWriter> clients;
    public Server(int port, String host, ArrayList clients) {
        this.port = port;
        this.host = host;
        this.clients = clients;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                new ClientHandler (this, serverSocket.accept()).start();
            }
        }
        catch (IOException e) {
        }
    }
}
class ClientHandler extends Thread {
    private Socket socket;
    static private PrintWriter out;
    static private BufferedReader in;
    public Server server;

    public ClientHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (server.clients) {
                server.clients.add(out);
            }
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                broadcast(message);
            }
        }
        catch (IOException e) {
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e){

            }
        }
    }
    private void broadcast(String message) {
        synchronized (server.clients) {
            for (PrintWriter client : server.clients) {
                client.println(message);
            }
        }
    }
}
