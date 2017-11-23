package com.naver.npns.server.controller;

import com.naver.npns.server.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushController {
    private static Logger log = LoggerFactory.getLogger(ConnectController.class);
    @Autowired
    private ClientService clientService;
}
