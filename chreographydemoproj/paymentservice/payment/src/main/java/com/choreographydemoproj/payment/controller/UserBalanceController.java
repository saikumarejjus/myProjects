package com.choreographydemoproj.payment.controller;

import com.choreographydemoproj.basedomains.dto.UserBalance;
import com.choreographydemoproj.payment.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class UserBalanceController {
    @Autowired
    private UserBalanceService userBalanceService;
    @PostMapping("/save")
    public String saveUserBalance(@RequestBody UserBalance userBalance)
    {
       return userBalanceService.saveUserBalance(userBalance);
    }

    @PostMapping("/update")
    public String updateUserBalance(@RequestBody UserBalance userBalance)
    {
        return userBalanceService.updateUserBalance(userBalance);
    }

}
