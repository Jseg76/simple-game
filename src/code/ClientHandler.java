package code;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String user;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.user = bufferedReader.readLine();
            clients.add(this);
            sendMessage("welcome " + user);
        }
        catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = bufferedReader.readLine();
                sendMessage(message);
            }
            catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    public void sendMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                if (!client.user.equals(user)) {
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            }
            catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    public void removeClient() {
        clients.remove(this);
    }
    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClient();
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
