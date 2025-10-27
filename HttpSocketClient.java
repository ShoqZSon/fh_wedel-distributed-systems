import java.io.*;
import java.net.Socket;

public class HttpSocketClient {
    public static void main(String[] args) throws IOException {
        String host = "www.fh-wedel.de";
        int port = 80;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // HTTP-Request senden
            out.println("GET / HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: close");
            out.println();
            out.flush();

            // HTTP-Response lesen
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
