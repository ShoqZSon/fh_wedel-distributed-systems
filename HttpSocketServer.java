import java.io.*;
import java.net.*;

public class HttpSocketServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server läuft auf Port " + port);

        // Open a thread everytime a new connection is made
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}
