package ex_06;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DataBaseServer {
    public void Main (String[] args) throws RemoteException, IOException{
       String stubFileName = Config.STUB_FILE;;

        DataBaseImpl db = new DataBaseImpl();
        Remote stub = UnicastRemoteObject.exportObject(db, 0);
        writeStubToFile(stubFileName, stub);
    }

    private static void writeStubToFile(String fileName, Remote stub)
            throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(stub);
        out.close();
    }
}
