package model;

import idl.PushReceiveService;

import java.util.HashMap;

public class ClientManager {

    private static ClientManager clientManager;
    private HashMap<String, PushReceiveService.Client> clientMapByUUID;

    public ClientManager() {
         clientMapByUUID = new HashMap<>();
    }

    public static ClientManager getInstanceClientManager() {
        if(clientManager == null)
            clientManager = new ClientManager();

        return clientManager;
    }

    public PushReceiveService.Client getClient(String uuid) {
        return clientMapByUUID.get(uuid);
    }

    public void putClient(String uuid, PushReceiveService.Client client) {
        clientMapByUUID.put(uuid, client);
    }



}
