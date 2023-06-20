package com.choreographydemoproj.payment.service;

import com.choreographydemoproj.basedomains.dto.*;
import com.choreographydemoproj.basedomains.repository.OrderEventRepo;
import com.choreographydemoproj.basedomains.repository.UserBalanceRepo;
import com.choreographydemoproj.basedomains.repository.UserTransactionRepo;
import com.choreographydemoproj.payment.kafa.PaymentProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckDeliveryEventService {

    private static Logger logger = LoggerFactory.getLogger(CheckDeliveryEventService.class);

    @Autowired
    private UserBalanceRepo userBalanceRepo;
    @Autowired
    private UserTransactionRepo userTransactionRepo;
    @Autowired
    private OrderEventRepo orderEventRepo;
    @Autowired
    private PaymentProducer paymentProducer;
    public void revertUserOrderPayment(OrderEvent orderEvent)
    {
        logger.info("entered into return method=============");
        String ordStat = orderEvent.getOrderStatus();
        String paymentStat = orderEvent.getPaymentStatus();
        String deliveryStat = orderEvent.getDeliverStatus();

        UserTransaction userTransaction =  userTransactionRepo.findByOrderId(orderEvent.getOrder().getOrderId()) ;
        int amount  = userTransaction.getTransactionAmount();
        logger.info("amount to be returned is ------"+amount);
        UserBalance userBalance = userBalanceRepo.findById(orderEvent.getOrder().getUserID()).get();
        logger.info("userbalance is --------"+userBalance.toString());

        if(ordStat.equals(OrderStatus.ORDER_CANCLED.toString()) &&
           paymentStat.equals(PaymentStatus.PAYMENT_RETURN.toString()) &&
            deliveryStat.equals(DeliveryStatus.DELIVERY_FAILED.toString()))
        {
            userBalance.setUserBalance(userBalance.getUserBalance()+amount);
            userBalanceRepo.save(userBalance);
            logger.info("payment returned to the user ");
            userTransactionRepo.delete(userTransaction);
            logger.info("payment user transaction is deleted ");

            orderEvent.setPaymentStatus(PaymentStatus.PAYMENT_RETURNED.toString());
            logger.info("payment status is changed to RETURNED ");

            orderEventRepo.save(orderEvent);
            paymentProducer.sendMessageToOrder(orderEvent);
            logger.info("delivery event is sent from payment to order  ");


        }
    }
}
