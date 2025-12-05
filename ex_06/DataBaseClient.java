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

        // Schreibt die Records
        String[] writeList = {"Appen","Ahrensburg","Wedel","Aumühle","Seevetal","Quickborn"};
        System.out.println("Schreibe Records in Datenbank...");
        for (int i = 0; i < writeList.length; i++) {
            int idx = 4101 + i;
            remote.addRecord(idx, writeList[i]);
            System.out.println("Geschriebene Records: (" + idx + writeList[i] + ")");
        }
        System.out.println("Records wurde geschrieben!");
        System.out.println();

        // Liest die Records
        int[] readList = {4103,4107};
        System.out.println("Lese Records aus Datenbank...");
        for (int i = 0; i < readList.length; i++) {
            System.out.println("Lesen des Records an Index: " + readList[i]);
            String record = remote.getRecord(readList[i]);
            System.out.println("Record: " + record);
        }
        System.out.println("Records wurden gelesen!");
        System.out.println();

        // Liest die Anzahl der Records
        int size = remote.getSize();
        System.out.println("Lesen der Anzahl an Records...");
        System.out.println("Anzahl der Records: " + size);
        if (size != writeList.length) {
            System.out.println("Fehler: Die Größe der Datenbank ist ungleich der geschriebenen Werte!");
        } else {
            System.out.println("Anzahl der Records ist korrekt!");
        }
        System.out.println("Records wurden gelesen!");
        System.out.println();
    }

    // Liest den Stub aus einer serialisierten Datei
    // Nutz den Stub um die DB Methoden zu verwenden aber dessen Implementierung nicht zu kennen
    private static DataBase readStubFromFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fis);
        DataBase remoteObj = (DataBase)in.readObject();
        in.close();
        return remoteObj;
    }
}
