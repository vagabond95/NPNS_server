package hello;

import model.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThriftController {


    @RequestMapping
    public String ping(@RequestBody Device device) {
        return "";
    }
}
