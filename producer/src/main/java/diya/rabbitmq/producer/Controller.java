package diya.rabbitmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    public final QueueSender queueSender;

    @Autowired
    public Controller(QueueSender queueSender) {
        this.queueSender = queueSender;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public void postMessage(@RequestBody String message) {

        queueSender.send(message);
    }
}
