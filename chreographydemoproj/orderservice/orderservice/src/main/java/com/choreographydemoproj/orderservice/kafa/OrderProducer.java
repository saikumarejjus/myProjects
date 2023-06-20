package com.choreographydemoproj.orderservice.kafa;

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

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String paymentTopic;

    public void sendMessage(OrderEvent orderEvent)
    {
        logger.info("event sent to kafka");

//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(orderEvent)
//                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
//                .build();
//        kafkaTemplate.send(message);
//        logger.info("event sent to kafka");
        kafkaTemplate.send(paymentTopic,orderEvent);
        logger.info(orderEvent.toString());
    }

}
