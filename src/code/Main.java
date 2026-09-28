package code;

import javax.swing.JFrame;
import java.awt.*;


public class Main {
    static int width = 800;
    static int height = 600;
    private static Canvas canvas;
    public static void main() {
        Game game = new Game();
        game.addFrame();
        Thread thread = new Thread(game);
        thread.start();
        game.running = true;
    }
    public static Canvas getCanvas() {
        return canvas;
    }


}




//package code;
//
//import java.awt.*;
//
//public class Main {
//    static int width = 800;
//    static int height = 600;
//    public void main() {
//        Game game = new Game();
//        Canvas canvas = new Canvas();
//        game.addFrame();
//    }
//}
