package controller;

import idl.PushReceiveService;
import model.ConnectResult;
import model.Device;
import model.TransportManager;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class ConnectController {
    private static Logger log = LoggerFactory.getLogger(ConnectController.class);

    private TransportManager transportManager;

    @PostConstruct
    public void post() {
        transportManager = TransportManager.getInstanceTransportManager();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/request_connect")
    public ConnectResult requestConnect(@RequestBody Device device) {
        log.warn("received device: {}", device);

        TTransport transport = connectDevice(device.getIp());
        String uuid = device.getUuid();
        if(transport != null) {
            transportManager.putTransport(uuid, transport);
        }

        String pingResult = sendPingToDevice(uuid);
        ConnectResult connectResult = new ConnectResult();
        connectResult.setPingResult(pingResult);
        log.warn("pingResult from ip {}: {}", device.getIp(), pingResult);
        return connectResult;
    }

    private String sendPingToDevice(String uuid) {

        String result = null;
        try {
            TTransport transport = transportManager.getTransport(uuid);
            TProtocol protocol = new TBinaryProtocol(transport);
            PushReceiveService.Client client = new PushReceiveService.Client(protocol);
            result = client.ping();
        } catch (TException e) {
            e.printStackTrace();
        }

        return result;
    }

    private TTransport connectDevice(String ip) {

        TTransport transport = null;

        try {
            transport = new TSocket(ip, 10000);

            transport.open();

        } catch (TTransportException e) {
            e.printStackTrace();
            log.debug("TTransportException {}", e);
        }

        return transport;

    }
}
