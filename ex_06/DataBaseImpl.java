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

    @Override
    public int getIndex(String record) throws RemoteException {
        for (Map.Entry<Integer, String> entry : database.entrySet()) {
            if (entry.getValue().equals(record)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    @Override
    public DBResult getRecordObj(int index) throws RemoteException {
        DBResult result = new DBResult();
        if (database.containsKey(index)) {
            result.setKey(index);
            result.setValue(database.get(index));
        } else {
            result.setKey(-1);
            result.setValue(null);
        }
        return result;
    }
}