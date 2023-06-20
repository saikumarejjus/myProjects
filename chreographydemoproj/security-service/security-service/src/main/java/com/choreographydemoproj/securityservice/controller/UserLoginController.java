package com.choreographydemoproj.securityservice.controller;

import com.choreographydemoproj.securityservice.entity.UserLoginDetails;
import com.choreographydemoproj.securityservice.repository.UserLoginDetailsRepo;
import com.choreographydemoproj.securityservice.service.UserLoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class UserLoginController {

    @Autowired
    private UserLoginDetailsService userLoginDetailsService;

    @PostMapping("/saveUser")
    public UserLoginDetails saveUserLoginDetails(@RequestBody UserLoginDetails userLoginDetails)
    {
        return userLoginDetailsService.saveUserLoginDetails(userLoginDetails);
    }

    @GetMapping("/getUser/{username}")
    public UserLoginDetails getUserLoginDetails(@PathVariable("username") String username)
    {
        return userLoginDetailsService.getUserLoginDetails( username);
    }
}
