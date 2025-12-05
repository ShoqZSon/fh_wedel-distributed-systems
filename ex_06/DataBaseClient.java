package ex_06;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataBaseClient {
    public static void main(String[] args) throws Exception {
        // Liest den Dateinamen aus der Config Klasse
        String stubFileName = Config.STUB_FILE;

        DataBase remote = readStubFromFile(stubFileName);
        remote.addRecord(4101, "Appen");
    }

    private static DataBase readStubFromFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        /* Deserialize Stub */
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fis);
        DataBase remoteObj = (DataBase)in.readObject();
        in.close();
        return remoteObj;
    }
}
