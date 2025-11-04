import logserver.LogMessageOuterClass.LogMessage;
import logserver.LogMessageOuterClass.SeverityLevel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class LogSocketClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        // Create a sample log message
        LogMessage logMessage = LogMessage.newBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setSource("MyApp")
                .setSeverity(SeverityLevel.INFO)
                .setMessage("This is a test log entry")
                .build();

        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream()) {

            // Serialize and send the log message as bytes
            logMessage.writeDelimitedTo(out);

            System.out.println("Log message sent to server!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
