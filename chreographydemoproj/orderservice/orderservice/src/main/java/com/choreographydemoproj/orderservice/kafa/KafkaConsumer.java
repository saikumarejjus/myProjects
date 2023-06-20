package com.choreographydemoproj.orderservice.kafa;

import com.choreographydemoproj.basedomains.dto.OrderEvent;
import com.choreographydemoproj.orderservice.service.CheckDeliveryEventService;
import com.choreographydemoproj.orderservice.service.CheckPaymentEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private  CheckPaymentEventService checkPaymentEventService;
    @Autowired
    private CheckDeliveryEventService checkDeliveryEventService;

    @KafkaListener(topics = "${spring.kafka.topic.name.payment-order}",
        groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderPaymentEvent(OrderEvent orderEvent)
    {
        logger.info("event is received from payment service" +orderEvent.toString());
        checkPaymentEventService.checkPaymentStatus(orderEvent);

    }

    @KafkaListener(topics = "${spring.kafka.topic.name.delivery-order}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderDeliveryEvent(OrderEvent orderEvent)
    {
        logger.info("event is received from delivery service" +orderEvent.toString());
        checkDeliveryEventService.checkDeliveryStatus(orderEvent);

    }



}
