package com.naver.npns.server.controller;

import com.naver.npns.server.idl.PushMessage;
import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.model.PushResult;
import com.naver.npns.server.service.ClientService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PushController {
    private static Logger log = LoggerFactory.getLogger(ConnectController.class);
    @Autowired
    private ClientService clientService;

    @PostMapping("/users/{uuid}/messages")
    public PushResult sendPushToDevice(@PathVariable String uuid, @RequestBody PushMessage pushMessage) {

        PushResult pushResult = new PushResult();
        try {
            PushReceiveService.Client client = clientService.getClient(uuid);
            pushResult.setPushResult(client.recv(pushMessage));
        } catch (TException e) {
            e.printStackTrace();
        }

        return pushResult;
    }

    @PostMapping("/broadcast-messages")
    public void sendPushToAllDevice(@RequestBody PushMessage pushMessage) {
        ArrayList<PushResult> pushResultArrayList = new ArrayList<>();
        HashMap<String, PushReceiveService.Client> clientMapByUUID = clientService.getClientMapByUUID();
        for ( String uuid : clientMapByUUID.keySet()) {
            try {
                PushResult pushResult = new PushResult();
                pushResult.setPushResult(clientMapByUUID.get(uuid).recv(pushMessage));
                pushResultArrayList.add(pushResult);
            } catch (TException e) {
                e.printStackTrace();
            }
        }
    }
}
