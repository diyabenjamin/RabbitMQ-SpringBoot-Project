package diya.rabbitmq.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public QueueListener createDatasetListener() {
        return new QueueListener();
    }

    @Bean(name = "listenerFactory")
    public SimpleRabbitListenerContainerFactory createListenerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setDefaultRequeueRejected(true);
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(retryInterceptor(3));
//        factory.setConcurrentConsumers(concurrencyForCache);
        return factory;
    }

    Advice retryInterceptor(int maxRetries) {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(maxRetries)
                .backOffOptions(1000, 3, 10000)
                .recoverer(new MessageRecoverer() {
                    @Override
                    public void recover(Message message, Throwable cause) {
                        System.out.println("Retry Exhausted");
                    }
                })
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
