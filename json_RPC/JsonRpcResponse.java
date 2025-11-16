package json_RPC;

public class JsonRpcResponse {
    public String jsonrpc = "2.0";
    public Object result;
    public Object error;
    public int id;

    public JsonRpcResponse(Object result, int id) {
        this.result = result;
        this.id = id;
    }

    public JsonRpcResponse(int id, String errorMessage, int code) {
        this.id = id;
        this.error = new Error(code, errorMessage);
    }

    public static class Error {
        public int code;
        public String message;

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
