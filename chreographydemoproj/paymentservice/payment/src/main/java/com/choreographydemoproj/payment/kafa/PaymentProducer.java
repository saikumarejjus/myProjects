package com.choreographydemoproj.payment.kafa;

import com.choreographydemoproj.basedomains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
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
public class PaymentProducer {

    private static Logger logger = LoggerFactory.getLogger(PaymentProducer.class);
    @Autowired
    private NewTopic newTopic;

    @Value("${spring.kafka.topic.name.payment-order}")
    String topicNameOrder ;

    @Value("${spring.kafka.topic.name.payment-delivery}")
    String topicNameDelivery ;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMessageToOrder(OrderEvent orderEvent)
    {
        logger.info("event sent to kafka");

//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(orderEvent)
//                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
//                .build();
        kafkaTemplate.send(topicNameOrder,orderEvent);
        logger.info("event sent to kafka from payment service to order service");
        logger.info(orderEvent.toString());
    }

    public void sendMessageToDelivery(OrderEvent orderEvent)
    {
        logger.info("event sent to kafka");

//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(orderEvent)
//                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
//                .build();
        kafkaTemplate.send(topicNameDelivery,orderEvent);
        logger.info("event sent to kafka from payment service to delivery service");
        logger.info(orderEvent.toString());
    }

}
