import database.DatabaseOuterClass.DBRequest;
import database.DatabaseOuterClass.DBResponse;
import database.DatabaseOuterClass.OperationType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DatabaseClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 9090;

        try (Socket socket = new Socket(host, port);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            // 1️⃣ Add records
            int[] indices = {4101, 4102, 4103, 4104, 4105, 4106};
            String[] values = {"Appen", "Ahrensburg", "Wedel", "Aumühle", "Seevetal", "Quickborn"};

            for (int i = 0; i < indices.length; i++) {
                DBRequest req = DBRequest.newBuilder()
                        .setOperation(OperationType.ADD_RECORD)
                        .setIndex(indices[i])
                        .setRecord(values[i])
                        .build();
                req.writeDelimitedTo(out);

                DBResponse resp = DBResponse.parseDelimitedFrom(in);
                System.out.println("Add " + indices[i] + ": " + resp.getSuccess());
            }

            // 2️⃣ Read records 4103 and 4107
            int[] queryIndices = {4103, 4107};
            for (int idx : queryIndices) {
                DBRequest req = DBRequest.newBuilder()
                        .setOperation(OperationType.GET_RECORD)
                        .setIndex(idx)
                        .build();
                req.writeDelimitedTo(out);

                DBResponse resp = DBResponse.parseDelimitedFrom(in);
                if (resp.getSuccess()) {
                    System.out.println("Record " + idx + ": " + resp.getRecord());
                } else {
                    System.out.println("Record " + idx + " not found (" + resp.getError() + ")");
                }
            }

            // 3️⃣ Get database size
            DBRequest sizeReq = DBRequest.newBuilder()
                    .setOperation(OperationType.GET_SIZE)
                    .build();
            sizeReq.writeDelimitedTo(out);

            DBResponse sizeResp = DBResponse.parseDelimitedFrom(in);
            System.out.println("Database size: " + sizeResp.getSize());
        }
    }
}
