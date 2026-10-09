package code;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server extends Thread {
    int port;
    DatagramSocket socket;
    public Server(int port) {
        try  {
            this.port = port;
            this.socket = new DatagramSocket(port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        System.out.println("server is up");
        while (true) {
            try {
                byte[] data = new byte[2048];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                System.out.println(data);
                send("hello".getBytes(StandardCharsets.UTF_8), packet.getAddress(), packet.getPort());
            }
            catch (IOException e) {
                System.out.println("no");
            }
        }
    }
    public void send(byte[] data, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
        }
    }
}
