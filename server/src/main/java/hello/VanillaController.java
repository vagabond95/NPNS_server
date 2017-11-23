package hello;

import model.Device;
import org.springframework.web.bind.annotation.*;

@RestController
public class VanillaController {


    @RequestMapping("/ping/")
    public String pong( @RequestBody Device device) {
        return " device: " + device;
    }
}
