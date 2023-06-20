package com.choreographydemoproj.payment.service;

import com.choreographydemoproj.basedomains.dto.UserBalance;
import com.choreographydemoproj.basedomains.repository.UserBalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {
    @Autowired
    private UserBalanceRepo userBalanceRepo;

    public String updateUserBalance(UserBalance userBalance) {
        userBalanceRepo.findAll().stream().filter(obj -> obj.getUserId() == userBalance.getUserId())
                .forEach(bal -> {
                            bal.setUserBalance(bal.getUserBalance() + userBalance.getUserBalance());
                        }
                );
        return "balance is updated";

    }
    public String saveUserBalance(UserBalance userBalance)
    {
        userBalanceRepo.save(userBalance);
        return "balance is saved";
    }



}
