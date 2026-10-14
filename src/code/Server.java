package code;

import java.io.*;
import java.net.*;

public class Server {
    ServerSocket serverSocket;
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void runServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Someone joined");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
            }
        }
        catch (IOException e) {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            }
            catch (IOException b) {
                b.printStackTrace();
            }
        }
    }
}
