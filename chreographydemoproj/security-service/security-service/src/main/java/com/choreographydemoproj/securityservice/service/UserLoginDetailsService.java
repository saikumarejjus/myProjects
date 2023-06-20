package com.choreographydemoproj.securityservice.service;

import com.choreographydemoproj.securityservice.entity.UserLoginDetails;
import com.choreographydemoproj.securityservice.repository.UserLoginDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginDetailsService {

    @Autowired
    private UserLoginDetailsRepo userLoginDetailsRepo;

    public UserLoginDetails saveUserLoginDetails(UserLoginDetails userLoginDetails)
    {
        return userLoginDetailsRepo.save(userLoginDetails);
    }

    public UserLoginDetails getUserLoginDetails(String username)
    {
        return userLoginDetailsRepo.findByUsername(username);
    }
}
