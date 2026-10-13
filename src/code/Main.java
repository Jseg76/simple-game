package code;

import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;


public class Main {
    static int width = 800;
    static int height = 600;
    static JFrame frame;
    private static Canvas canvas;
    static int scale = 1;
    static int FPS = 60;
    static int port = 8888;

    public static void main() throws IOException {
        Game game = new Game();
        frame =  game.addFrame();

        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        server.runServer();

        Thread gameThread = new Thread(game);
        gameThread.start();
        game.running = true;
    }
}
