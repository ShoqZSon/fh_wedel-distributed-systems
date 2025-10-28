import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        //
        try (
            // try-catch with resources
            // Closes the resources when the try block is finished automatically
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream())
        ) {
            // Construction of the HTTP-response
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

            String body = "<html><body><h1>Hallo vom Java-Server (Thread: "
                    + Thread.currentThread().threadId() + ")</h1></body></html>";

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("Content-Length: " + body.length());
            out.println();
            out.println(body);
            out.flush();

        } catch (IOException e) {
            System.err.println("Error handling client connection");
            System.err.println("Reason: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
