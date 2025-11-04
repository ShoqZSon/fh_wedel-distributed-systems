import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpSocketClient_URL {
    public static void main(String[] args) throws IOException {
        Proxy proxy = new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress("127.0.0.1", 8888)
        );


        URL url = new URL("http://localhost:8080/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }

        conn.disconnect();
    }
}
