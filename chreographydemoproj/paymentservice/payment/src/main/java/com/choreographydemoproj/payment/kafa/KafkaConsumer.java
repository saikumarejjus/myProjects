package com.choreographydemoproj.payment.kafa;

 import com.choreographydemoproj.basedomains.dto.OrderEvent;
 import com.choreographydemoproj.payment.service.CheckDeliveryEventService;
 import com.choreographydemoproj.payment.service.CheckOrderService;
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private CheckOrderService checkOrderService;

    @Autowired
    private CheckDeliveryEventService checkDeliveryEventService;
    @KafkaListener(topics = "${spring.kafka.topic.name}",
        groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderEvent orderEvent)
    {
        logger.info("event is received  order service :"+orderEvent.toString());
        checkOrderService.validateOrderAmount(orderEvent);

    }

    @KafkaListener(topics = "${spring.kafka.topic.name.delivery-payment}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDeliveryEvent(OrderEvent orderEvent)
    {
        logger.info("event is received from delivery service :"+orderEvent.toString());
        checkDeliveryEventService.revertUserOrderPayment(orderEvent);

    }



}
