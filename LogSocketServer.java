import logserver.LogMessageOuterClass;
import logserver.LogMessageOuterClass.LogMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

// Main server
public class LogSocketServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server läuft auf Port " + port);

        // Open a thread every time a new connection is made
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new LogClientHandler(clientSocket)).start();
        }
    }
}

// Client handler for each connection
class LogClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Path LOG_FILE = Path.of("logs.txt");

    public LogClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream in = clientSocket.getInputStream()) {
            // Read a single LogMessage from the client
            LogMessage logMessage = LogMessage.parseDelimitedFrom(in);

            // Print out the client message
            if (logMessage != null) {
                long timestamp = logMessage.getTimestamp();
                String source = logMessage.getSource();
                LogMessageOuterClass.SeverityLevel severity = logMessage.getSeverity();
                String message = logMessage.getMessage();
                System.out.println("Received log:");
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Source: " + source);
                System.out.println("Severity: " + severity);
                System.out.println("Message: " + message);


                String logEntry = String.format(
                        "[%d] [%s] [%s]: %s%n",
                        timestamp,
                        source,
                        severity,
                        message
                );

                // Append to file in a thread-safe way
                synchronized (LogClientHandler.class) {
                    Files.writeString(LOG_FILE, logEntry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }

                System.out.println("Log written to file: " + logEntry.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
