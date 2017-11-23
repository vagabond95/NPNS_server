package controller;

import model.ClientManager;
import model.TransportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public class PushController {

    private static Logger log = LoggerFactory.getLogger(ConnectController.class);
    private ClientManager clientManager;

    @PostConstruct
    public void post() {
        clientManager = ClientManager.getInstanceClientManager();
    }



}
