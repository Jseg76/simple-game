package code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Thread {
    int port;
    InetAddress address;
    DatagramSocket socket;

    public Client(String host) {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(host);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        System.out.println("client is up");
        while(true) {
            try {
                byte[] data = new byte[2048];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.socket.receive(packet);
                System.out.println(new String(data));
            } catch (IOException e) {
            }
        }
    }
    public void send(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, this.address, this.port);

        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
