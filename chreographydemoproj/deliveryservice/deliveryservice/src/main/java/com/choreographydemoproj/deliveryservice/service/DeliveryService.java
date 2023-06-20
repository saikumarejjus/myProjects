package com.choreographydemoproj.deliveryservice.service;

import com.choreographydemoproj.basedomains.dto.DeliveryStatus;
import com.choreographydemoproj.basedomains.dto.OrderEvent;
import com.choreographydemoproj.basedomains.dto.OrderStatus;
import com.choreographydemoproj.basedomains.dto.PaymentStatus;
import com.choreographydemoproj.basedomains.repository.OrderEventRepo;
 import com.choreographydemoproj.deliveryservice.entity.DelivetyDto;
import com.choreographydemoproj.deliveryservice.kafa.KafkaConsumer;
import com.choreographydemoproj.deliveryservice.kafa.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private static Logger logger = LoggerFactory.getLogger(DeliveryService.class);


    public OrderEvent orderEvent = null;
    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private OrderEventRepo orderEventRepo;

    public String updateOrderDeliveryStatus(DelivetyDto delivetyDto)
    {
        orderEvent = KafkaConsumer.orderEventObj;
        logger.info("event from payment service --------"+orderEvent);
       OrderEvent orderEvent1 =  orderEventRepo.findById(delivetyDto.geteventId()).get();
        logger.info(orderEvent1.toString());
        if(delivetyDto.geteventId().equals(orderEvent1.getEventId()))
         return this.updateDeliveryStatus(delivetyDto);
        else return "entered event id does not exists";

    }

    private String updateDeliveryStatus(DelivetyDto delivetyDto)
    {
        if (orderEvent.getDeliverStatus().equals(DeliveryStatus.DELIVERY_COMPLETED.toString()))
        {
            return "the order with"+orderEvent.getEventId()+" this event id is delivered and you can not change the status or enter the correct EventID ";
        }
        else {
            if(delivetyDto.getOrderStatus())
            {
                orderEvent.setDeliverStatus(DeliveryStatus.DELIVERY_COMPLETED.toString());
                orderEvent.setOrderStatus(OrderStatus.ORDER_COMPLETED.toString());
                orderEventRepo.save(orderEvent);
                orderProducer.sendMessageOrder(orderEvent);
                return "order status has been change to delivered";
            }
            else
            {
                orderEvent.setDeliverStatus(DeliveryStatus.DELIVERY_FAILED.toString());
                orderEvent.setOrderStatus(OrderStatus.ORDER_CANCLED.toString());
                orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_RETURN.toString());
                orderEventRepo.save(orderEvent);

                orderProducer.sendMessagePayment(orderEvent);
                return "order status is changed to not delivered and returning of payment is initiated";

            }
        }

    }
}
