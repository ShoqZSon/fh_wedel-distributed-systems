package ex_06;


import java.rmi.RemoteException;

public class DataBaseImpl implements DataBase {
    @Override
    public String getRecord(int index) throws RemoteException {
        return null;
    }

    @Override
    public void addRecord(int index, String record) throws RemoteException {

    }

    @Override
    public int getSize() throws RemoteException {
        return 0;
    }
}