package com.choreographydemoproj.payment.service;

import com.choreographydemoproj.basedomains.dto.*;
import com.choreographydemoproj.basedomains.repository.*;
import com.choreographydemoproj.payment.feignclient.ProductDetailsClient;
import com.choreographydemoproj.payment.kafa.PaymentProducer;
import com.choreographydemoproj.payment.response.ProductDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckOrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserBalanceRepo userBalanceRepo;
//    @Autowired
//    private ProductDetailsRepo productDetailsRepo;
    @Autowired
    private UserTransactionRepo userTransactionRepo;
    @Autowired
    private PaymentProducer paymentProducer;
    @Autowired
    private OrderEventRepo orderEventRepo;

    private static Logger logger = LoggerFactory.getLogger(CheckOrderService.class);

    public void validateOrderAmount(@NonNull OrderEvent orderEvent)
    {
        String ordStatus = orderEvent.getOrderStatus();
        OrderDetails orderDetails ;
        orderDetails = orderEvent.getOrder();
        //OrderEvent ordEvent = orderEventRepo.findById(orderEvent.getEventId()).get();

        getOrderAmount(orderDetails);

        UserBalance userBalance  = userBalanceRepo.findById(orderEvent.getOrder().getUserID()).get();

        if(userBalance.getUserBalance()>totalCost)
        {
            userBalanceRepo.save(new UserBalance(orderEvent.getOrder().getUserID(),userBalance.getUserBalance()-totalCost));
            userTransactionRepo.save(new UserTransaction(orderEvent.getOrder().getUserID(),totalCost,orderEvent.getOrder().getOrderId()));
            orderEvent.setOrderStatus(OrderStatus.ORDER_CREATED.toString());
            orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED.toString());
            orderEvent.setDeliverStatus(DeliveryStatus.DELIVERY_PENDING.toString());
            orderEventRepo.save(orderEvent);
            logger.info("payment is completed");
            paymentProducer.sendMessageToDelivery(orderEvent);
            logger.info("payment event is sent from payment to delivery ");


        }
        else
        {
            logger.info("you have insufficient balance for completing for payment");
            orderEvent.setOrderStatus(OrderStatus.ORDER_CANCLED.toString());
            orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_NOTCOMPLETED.toString());
            orderEvent.setDeliverStatus(DeliveryStatus.DELIVERY_PENDING.toString());
            orderEventRepo.save(orderEvent);
            paymentProducer.sendMessageToOrder(orderEvent);
            logger.info("payment event is sent from payment to order  ");



        }
//        userBalanceRepo.findAll().stream()
//                .filter( obj -> obj.getUserId()==101).forEach( bal -> {
//                    if(bal.getUserBalance()>totalCost)
//                    {
//                        userBalanceRepo.save(new UserBalance(101,bal.getUserBalance()-totalCost));
//                        userTransactionRepo.save(new UserTransaction(0,101,totalCost));
//                        orderEvent.setOrderStatus(OrderStatus.ORDER_CREATED.toString());
//                        orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED.toString());
//                        paymentProducer.sendMessage(orderEvent);
//
//                    }
//                    else
//                    {
//                        logger.info("updatin or details");
//                        orderEvent.setOrderStatus(OrderStatus.ORDER_CANCLED.toString());
//                        orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_NOTCOMPLETED.toString());
//                        paymentProducer.sendMessage(orderEvent);
//
//
//                    }
//
//        });

    }

    int totalCost =0;
    @Autowired
    private ProductDetailsClient productDetailsClient;
    public void getOrderAmount(OrderDetails orderDetails)
    {
         Optional<ProductDetails> productDetails = productDetailsClient.getProductDetails(orderDetails.getProductId());
              //   productDetails.stream().forEach( obj -> totalCost = orderDetails.getProductQuant()* obj.getProductPrice());
         productDetails.ifPresent( obj -> totalCost = orderDetails.getProductQuant()* obj.getProductPrice());


    }



}
