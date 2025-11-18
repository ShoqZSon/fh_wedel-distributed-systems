package json_RPC;

import jakarta.json.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class DatabaseServerJsonRpc {

    private static final Map<Integer, String> database = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090);
        System.out.println("JSON-RPC Server läuft auf Port 9090");

        while (true) {
            Socket client = server.accept();
            new Thread(() -> handle(client)).start();
        }
    }

    private static void handle(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {

                JsonRpcRequest req = JsonRpcCodec.decodeRequest(line);
                JsonRpcResponse resp;

                switch (req.method) {

                    case "addRecord" -> {
                        JsonObject a = (JsonObject) req.params;
                        database.put(a.getInt("index"), a.getString("record"));
                        resp = new JsonRpcResponse(Json.createObjectBuilder()
                                .add("success", true).build(), req.id);
                    }

                    case "getRecord" -> {
                        JsonObject g = (JsonObject) req.params;
                        int idx = g.getInt("index");
                        String val = database.get(idx);

                        resp = (val != null) ?
                                new JsonRpcResponse(Json.createObjectBuilder()
                                        .add("record", val).build(), req.id) :
                                new JsonRpcResponse(req.id, "Record not found", -1);
                    }

                    case "getSize" -> resp = new JsonRpcResponse(
                            Json.createObjectBuilder().add("size", database.size()).build(),
                            req.id);

                    default -> resp = new JsonRpcResponse(req.id, "Unknown method", -32601);
                }
                out.println(JsonRpcCodec.encodeResponse(resp));
                System.out.println("[DEBUG] Sent response with id: " + resp.id);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}
