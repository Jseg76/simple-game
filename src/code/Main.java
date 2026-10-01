package code;

import javax.swing.JFrame;
import java.awt.*;


public class Main {
    static JFrame frame;
    static int width = 800;
    static int height = 600;
    static int FPS = 60;
    public static void main() {
        Game game = new Game();
        frame = game.addFrame();
        Thread thread = new Thread(game);
        thread.start();
        game.running = true;
    }
}
