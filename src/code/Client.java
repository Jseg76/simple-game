package code;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String user;

    public Client(Socket socket, String user) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.user = user;
        }
        catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }
    public void sendMessage() {
        try {
            bufferedWriter.write(user);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(user + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }
    public void listen() {
        new Thread(new Runnable() {
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        closeAll(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user");
        String user = scanner.nextLine();
        Socket socket = new Socket("localhost", 8888);
        Client client = new Client(socket, user);
        client.listen();
        client.sendMessage();
    }
}
