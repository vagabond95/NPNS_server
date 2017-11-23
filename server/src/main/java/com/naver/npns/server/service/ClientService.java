package com.naver.npns.server.service;

import com.naver.npns.server.idl.PushReceiveService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;

@Service
public class ClientService {
    private static HashMap<String, PushReceiveService.Client> clientMapByUUID = new HashMap<>();

    public static HashMap<String, PushReceiveService.Client> getClientMapByUUID() {
        return clientMapByUUID;
    }

    public PushReceiveService.Client getClient(String uuid) {
        return clientMapByUUID.get(uuid);
    }

    public void putClient(String uuid, PushReceiveService.Client client) {
        clientMapByUUID.put(uuid, client);
    }
}
