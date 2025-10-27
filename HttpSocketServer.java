import java.io.*;
import java.net.*;

public class HttpSocketServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server läuft auf Port " + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {

                // HTTP-Request lesen
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    System.out.println(line);
                }

                // HTTP-Response senden
                String body = "<html><body><h1>Hallo vom Java-Server</h1></body></html>";
                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Content-Type: text/html; charset=UTF-8\r\n");
                out.print("Content-Length: " + body.length() + "\r\n");
                out.print("\r\n");
                out.print(body);
                out.flush();
            }
        }
    }
}
