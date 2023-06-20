package com.choreographydemoproj.orderservice.controller;

import com.choreographydemoproj.basedomains.dto.*;
import com.choreographydemoproj.basedomains.repository.OrderEventRepo;
import com.choreographydemoproj.basedomains.repository.OrderRepo;
import com.choreographydemoproj.orderservice.kafa.KafkaConsumer;
import com.choreographydemoproj.orderservice.kafa.OrderProducer;

import jakarta.persistence.GeneratedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class Ordercontroller {

    private static Logger logger = LoggerFactory.getLogger(Ordercontroller.class);

    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private OrderEventRepo orderEventRepo;

    @Autowired
   private OrderRepo orderRepo;
    @PostMapping("/save")
    public String saveOrder(@RequestBody OrderDetails order)
    {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(UUID.randomUUID().toString());
        orderEvent.setMessage("the order status is pending");
        orderEvent.setOrderStatus(OrderStatus.ORDER_CREATED.toString());
        orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_NOTCOMPLETED.toString());
        orderEvent.setDeliverStatus(DeliveryStatus.DELIVERY_PENDING.toString());
        orderEvent.setOrder(order);
        orderRepo.save(order);
        orderEventRepo.save(orderEvent);


        orderProducer.sendMessage(orderEvent);
        logger.info("order event is sent from  order to payment ");

        return "order is initialized";
    }

}
