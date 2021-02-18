package diya.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@RabbitListener(queues = "${queue.name}", containerFactory = "listenerFactory")
public class QueueListener {
    Logger LOG = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    ObjectMapper objectMapper;

    @RabbitHandler
    public void receive(String message) throws JsonProcessingException {
        System.out.println(new Date());
        Person person = objectMapper.readValue(message, Person.class);
        LOG.info("Received: {} Hobbies:{}", message, person.hobbies);
        System.out.println("Converted Value: " + objectMapper.writeValueAsString(person));

    }
}
