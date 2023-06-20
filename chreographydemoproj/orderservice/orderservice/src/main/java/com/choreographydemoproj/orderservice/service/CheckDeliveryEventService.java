package com.choreographydemoproj.orderservice.service;

import com.choreographydemoproj.basedomains.dto.DeliveryStatus;
import com.choreographydemoproj.basedomains.dto.OrderEvent;
import com.choreographydemoproj.basedomains.dto.OrderStatus;
import com.choreographydemoproj.basedomains.dto.PaymentStatus;
import com.choreographydemoproj.basedomains.repository.OrderEventRepo;
import com.choreographydemoproj.basedomains.repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckDeliveryEventService {

    private static final Logger logger = LoggerFactory.getLogger(CheckPaymentEventService.class);
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderEventRepo orderEventRepo;
    public void checkDeliveryStatus(OrderEvent orderEvent)
    {
        String ordStatus = orderEvent.getOrderStatus();
        String paymentStat = orderEvent.getPaymentStatus();
        String deliveryStat = orderEvent.getDeliverStatus();
        if(ordStatus.equals(OrderStatus.ORDER_CREATED.toString()) &&
                paymentStat.equals(PaymentStatus.PAYMENT_COMPLETED.toString()) &&
                deliveryStat.equals(DeliveryStatus.DELIVERY_COMPLETED.toString()))
        {
            orderEvent.setOrderStatus(OrderStatus.ORDER_COMPLETED.toString());
            orderEventRepo.save(orderEvent);
            logger.info("order is completed successfully");


        }
//        else if (ordStatus.equals(PaymentStatus.PAYMENT_CANCELED.toString()) &&
//                paymentStat.equals(OrderStatus.ORDER_CANCLED.toString()))
//        {
//            orderEvent.setOrderStatus(OrderStatus.ORDER_CANCLED.toString());
//            orderRepo.delete(orderEvent.getOrder());
//            logger.info("order is cancled  because of payment is not done");
//        }
    }
}
