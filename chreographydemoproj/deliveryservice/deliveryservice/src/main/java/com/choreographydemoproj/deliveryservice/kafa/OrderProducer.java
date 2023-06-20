package com.choreographydemoproj.deliveryservice.kafa;

import com.choreographydemoproj.basedomains.dto.OrderEvent;
 import org.apache.kafka.clients.admin.NewTopic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    @Autowired
    private NewTopic newTopic;

    @Value("$spring.kafka.topic.name.delivery-order")
    private String topicOrder;
    @Value("$spring.kafka.topic.name.delivery-payment")
    private String topicPayment;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMessageOrder(OrderEvent orderEvent)
    {
        logger.info("event sent from delivery service to kafka order service");

//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(orderEvent)
//                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
//                .build();
//        kafkaTemplate.send(message);
        kafkaTemplate.send("delivery-order-topic",orderEvent);
        logger.info(orderEvent.toString());
    }

    public void sendMessagePayment(OrderEvent orderEvent)
    {
        logger.info("event sent from delivery service to kafka payment service");

//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(orderEvent)
//                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
//                .build();
//        kafkaTemplate.send(message);
        kafkaTemplate.send("delivery-payment-topic",orderEvent);
        logger.info(orderEvent.toString());
    }

}
