package json_RPC;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.*;
import java.net.Socket;

public class DatabaseClientJsonRpc {

    public static void main(String[] args) throws IOException {

        try (Socket s = new Socket("localhost", 9090);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            // 1Add records
            int[] keys = {4101, 4102, 4103, 4104, 4105, 4106};
            String[] vals = {"Appen", "Ahrensburg", "Wedel", "Aumühle", "Seevetal", "Quickborn"};

            for (int i = 0; i < keys.length; i++) {
                JsonObject params = Json.createObjectBuilder()
                        .add("index", keys[i])
                        .add("record", vals[i])
                        .build();

                JsonRpcRequest req = new JsonRpcRequest("addRecord", params, i + 1);
                out.println(JsonRpcCodec.encodeRequest(req));
                System.out.println("Add: " + in.readLine());
            }

            // Read records
            int[] read = {4103, 4107};
            for (int k : read) {
                JsonRpcRequest req = new JsonRpcRequest(
                        "getRecord",
                        Json.createObjectBuilder().add("index", k).build(),
                        100 + k
                );
                out.println(JsonRpcCodec.encodeRequest(req));
                System.out.println("Get: " + in.readLine());
            }

            // DB size
            JsonRpcRequest reqSize = new JsonRpcRequest(
                    "getSize",
                    Json.createObjectBuilder().build(),
                    999
            );
            out.println(JsonRpcCodec.encodeRequest(reqSize));
            System.out.println("Size: " + in.readLine());
        }
    }
}
