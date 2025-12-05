package ex_06;


import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseImpl implements DataBase {
    private static final Map<Integer, String> database = new ConcurrentHashMap<>();

    @Override
    public String getRecord(int index) throws RemoteException {
        String record = null;
        if (database.containsKey(index)) {
            record = database.get(index);
        }
        return record;
    }

    @Override
    public void addRecord(int index, String record) throws RemoteException {
        database.put(index, record);
    }

    @Override
    public int getSize() throws RemoteException {
        return database.size();
    }
}