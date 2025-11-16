package json_RPC;

public class JsonRpcRequest {
    public String jsonrpc = "2.0";
    public String method;
    public Object params;
    public int id;

    public JsonRpcRequest(String method, Object params, int id) {
        this.method = method;
        this.params = params;
        this.id = id;
    }
}
