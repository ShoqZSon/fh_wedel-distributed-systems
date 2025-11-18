package json_RPC;

import jakarta.json.*;
import java.io.StringReader;

public class JsonRpcCodec {

    // Request -> JSON String
    public static String encodeRequest(JsonRpcRequest req) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("jsonrpc", "2.0")
                .add("method", req.method)
                .add("id", req.id);

        if (req.params instanceof JsonObject paramsObj) {
            builder.add("params", paramsObj);
        }

        return builder.build().toString();
    }

    // JSON String -> Request
    public static JsonRpcRequest decodeRequest(String json) {
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        String method = obj.getString("method");
        int id = obj.getInt("id");

        JsonObject params = null;
        if (obj.containsKey("params")) {
            params = obj.getJsonObject("params");
        }

        return new JsonRpcRequest(method, params, id);
    }

    // Response -> JSON String
    public static String encodeResponse(JsonRpcResponse resp) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("jsonrpc", "2.0")
                .add("id", resp.id);

        if (resp.error != null) {
            JsonRpcResponse.Error err = (JsonRpcResponse.Error) resp.error;
            builder.add("error", Json.createObjectBuilder()
                    .add("code", err.code)
                    .add("message", err.message));
        } else if (resp.result instanceof JsonObject resultObj) {
            builder.add("result", resultObj);
        }

        return builder.build().toString();
    }

    // JSON String -> Response
    public static JsonRpcResponse decodeResponse(String json) {
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        int id = obj.getInt("id");

        if (obj.containsKey("error")) {
            JsonObject e = obj.getJsonObject("error");
            return new JsonRpcResponse(id, e.getString("message"), e.getInt("code"));
        }

        return new JsonRpcResponse(obj.get("result"), id);
    }
}
