package diya.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueueSender {
    @Autowired
    private RabbitTemplate template;

    @Resource(name = "exchange")
    // @Autowired
    private DirectExchange direct;

    Logger LOG = LoggerFactory.getLogger(QueueSender.class);

    public void send(String message) {
        String queueName = "diya.queue";
        LOG.info("Sending message via {}@{}: {}", direct.getName(), queueName, message);
        this.template.convertAndSend(direct.getName(), queueName, message);
    }
}
