package model;

import org.apache.thrift.transport.TTransport;

import java.util.HashMap;

public class TransportManager {

    private static TransportManager transportManager;
    private HashMap<String, TTransport> transportMapByUUID;

    public static TransportManager getInstanceTransportManager() {
        if(transportManager == null)
            transportManager = new TransportManager();

        return transportManager;
    }

    public TransportManager() {
        transportMapByUUID = new HashMap<>();
    }

    public TTransport getTransport(String uuid) {
        return transportMapByUUID.get(uuid);
    }

   public void putTransport(String uuid, TTransport transport) {
        transportMapByUUID.put(uuid, transport);
   }


}
