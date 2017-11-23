package controller;

import idl.PushReceiveService;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

@Controller
public class ConnectController {
    private static Logger log = LoggerFactory.getLogger(ConnectController.class);

    private TransportManager transportManager;

    @PostConstruct
    public void post() {
        transportManager = TransportManager.getInstanceTransportManager();
    }

    @RequestMapping("/ping/")
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "/request_connect/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestConnect(@RequestBody Device device) {
        log.warn("received device: {}", device);

        TTransport transport = connectDevice(device.getIp());
        String uuid = device.getUuid();
        if(transport != null) {
            transportManager.putTransport(uuid, transport);
        }

        String pingResult = ping(uuid);
        log.warn("pingResult from ip {}: {}", device.getIp(), pingResult);
        return "pingResult = " +pingResult;
    }

    private String ping(String uuid) {

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
        } catch (TException e) {
            e.printStackTrace();
            log.debug("TException {}", e);
        }

        return transport;

    }
}
