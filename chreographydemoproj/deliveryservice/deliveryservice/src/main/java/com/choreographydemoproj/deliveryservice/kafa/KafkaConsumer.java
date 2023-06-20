package com.choreographydemoproj.deliveryservice.kafa;

import com.choreographydemoproj.basedomains.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    public static OrderEvent orderEventObj;
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = "${spring.kafka.topic.name.payment-delivery}",
        groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderEvent orderEvent)
    {
        orderEventObj= orderEvent;
        logger.info("event is received from payment service" +orderEvent.toString());

    }



}
