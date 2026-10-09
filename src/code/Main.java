package code;

import javax.swing.JFrame;
import java.awt.*;


public class Main {
    static int width = 800;
    static int height = 600;
    static JFrame frame;
    private static Canvas canvas;
    static int scale = 1;
    static int FPS = 60;

    public static void main() {
        Game game = new Game();
        frame =  game.addFrame();
        Server server = new Server(2000);
        server.start();

        Client client = new Client("localhost");
        client.start();

        Thread thread = new Thread(game);
        thread.start();
        game.running = true;
    }
}
