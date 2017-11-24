package com.naver.npns.server.service;

import com.naver.npns.server.idl.PushMessage;
import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.model.PushResult;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PushService {

    @Autowired
    private ClientService clientService;
    private int seq = 0;

    public int getSeq() {
        return ++seq;
    }

    public PushResult sendPushToDevice(String uuid, PushMessage pushMessage) {

        PushResult pushResult = new PushResult();
        try {
            PushReceiveService.Client client = clientService.getClient(uuid);
            pushResult.setPushResult(client.recv(pushMessage));
        } catch (TException e) {
            e.printStackTrace();
        }
        return pushResult;
    }

    public ArrayList<PushResult> sendPushToAllDevice(@RequestBody PushMessage pushMessage) {
        ArrayList<PushResult> pushResultArrayList = new ArrayList<>();
        HashMap<String, PushReceiveService.Client> clientMapByUUID = clientService.getClientMapByUUID();
        for (String uuid : clientMapByUUID.keySet()) {
            pushResultArrayList.add(sendPushToDevice(uuid, pushMessage));
        }
        return pushResultArrayList;
    }
}
