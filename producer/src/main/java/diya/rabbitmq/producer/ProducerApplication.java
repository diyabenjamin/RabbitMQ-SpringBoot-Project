package diya.rabbitmq.producer;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("diya.exchange");
    }

    @Bean
    Queue queue() {
        return new Queue("diya.queue", false);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).withQueueName();
    }
}
