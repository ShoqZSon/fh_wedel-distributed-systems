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
        String[] recordWriteList = {"Appen","Ahrensburg","Wedel","Aumühle","Seevetal","Quickborn"};
        System.out.println("Schreibe Records in Datenbank...");
        for (int i = 0; i < recordWriteList.length; i++) {
            int idx = 4101 + i;
            remote.addRecord(idx, recordWriteList[i]);
            System.out.println("Geschriebene Records: (" + idx + recordWriteList[i] + ")");
        }
        System.out.println("Records wurde geschrieben!");
        System.out.println();

        // Liest die Records
        int[] recordReadList = {4103,4107};
        System.out.println("Lese Records aus Datenbank...");
        for (int i = 0; i < recordReadList.length; i++) {
            System.out.println("Lesen des Records an Index: " + recordReadList[i]);
            String record = remote.getRecord(recordReadList[i]);
            System.out.println("Record: " + record);
        }
        System.out.println("Records wurden gelesen!");
        System.out.println();

        // Liest die Anzahl der Records
        int size = remote.getSize();
        System.out.println("Lesen der Anzahl an Records...");
        System.out.println("Anzahl der Records: " + size);
        if (size != recordWriteList.length) {
            System.out.println("Fehler: Die Größe der Datenbank ist ungleich der geschriebenen Werte!");
        } else {
            System.out.println("Anzahl der Records ist korrekt!");
        }
        System.out.println("Records wurden gelesen!");
        System.out.println();

        // 4101 sollte Appen ausgeben
        // 4107 sollte -1 ausgeben
        System.out.println("Lesen der Indexe der Records...");
        String[] indexReadList = {"Appen", "Buxtehude"};
        for (int i = 0; i < indexReadList.length; i++) {
            System.out.println("Lesen des Index von Record: " + indexReadList[i]);
            int index = remote.getIndex(indexReadList[i]);
            if (index < 0) {
                System.out.println("Fehler beim Lesen (ERROR: " + index + ")");
            } else {
                System.out.println("Index von Record '" + indexReadList[i] + "': " + index);
            }
        }
        System.out.println();

        // Versuch von DBResult -> getRecordObj
        System.out.println("Ausgabeversuch eines GÜLTIGEN DBResult Objekts");
        int index = 4101;
        DBResult res = remote.getRecordObj(index);
        System.out.println("Gelesenes Objekt mit Key: " + res.getKey() + " und Wert: " + res.getValue());System.out.println("Ausgabeversuch eines gültigen DBResult Objekts");
        System.out.println();
        System.out.println("Ausgabeversuch eines UNGÜLTIGEN DBResult Objekts");
        index = 1;
        res = remote.getRecordObj(index);
        System.out.println("Gelesenes Objekt mit Key: " + res.getKey() + " und Wert: " + res.getValue());
    }

    // Liest den Stub aus einer serialisierten Datei
    // Nutz den Stub um die DB Methoden zu verwenden ohne dessen Implementierung zu kennen
    private static DataBase readStubFromFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fis);
        DataBase remoteObj = (DataBase)in.readObject();
        in.close();
        return remoteObj;
    }
}
