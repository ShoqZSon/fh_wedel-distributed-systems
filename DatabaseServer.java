import database.DatabaseOuterClass.DBRequest;
import database.DatabaseOuterClass.DBResponse;
import database.DatabaseOuterClass.OperationType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class DatabaseServer {

    private static final Map<Integer, String> database = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        int port = 9090;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Database server running on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new DBClientHandler(clientSocket)).start();
        }
    }

    private static class DBClientHandler implements Runnable {
        private final Socket clientSocket;

        public DBClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (InputStream in = clientSocket.getInputStream();
                 OutputStream out = clientSocket.getOutputStream()) {

                DBRequest request;
                while ((request = DBRequest.parseDelimitedFrom(in)) != null) {

                    // Log incoming instruction to terminal
                    System.out.println("Received request: " + request.getOperation() +
                            " | index=" + request.getIndex() +
                            (!request.getRecord().isEmpty() ? (" | record=\"" + request.getRecord() + "\"") : ""));

                    DBResponse.Builder response = DBResponse.newBuilder();

                    switch (request.getOperation()) {

                        case ADD_RECORD:
                            database.put(request.getIndex(), request.getRecord());
                            response.setSuccess(true);
                            System.out.println("   Record added.");
                            break;

                        case GET_RECORD:
                            String value = database.get(request.getIndex());
                            if (value != null) {
                                response.setSuccess(true).setRecord(value);
                                System.out.println("   Record found: \"" + value + "\"");
                            } else {
                                response.setSuccess(false)
                                        .setError("Record not found for index " + request.getIndex());
                                System.out.println("    No record found.");
                            }
                            break;

                        case GET_SIZE:
                            response.setSuccess(true).setSize(database.size());
                            System.out.println("   Database size: " + database.size());
                            break;
                    }

                    response.build().writeDelimitedTo(out);
                }

            } catch (IOException e) {
                System.err.println("Client connection error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Failed closing socket.");
                }
            }
        }
    }
}
