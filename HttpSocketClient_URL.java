import java.io.IOException;
import java.net.*;
import java.io.*;

public class HttpSocketClient_URL {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.google.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
