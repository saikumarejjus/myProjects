package com.choreographydemoproj.deliveryservice.controller;

import com.choreographydemoproj.deliveryservice.entity.DelivetyDto;
import com.choreographydemoproj.deliveryservice.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @PostMapping("/update")
    private String updateDeliveryStatus(@RequestBody DelivetyDto delivetyDto)
    {
        return deliveryService.updateOrderDeliveryStatus(delivetyDto);
    }
}
