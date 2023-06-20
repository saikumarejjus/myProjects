package com.choreographydemoproj.securityservice.repository;

import com.choreographydemoproj.securityservice.entity.UserLoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginDetailsRepo extends JpaRepository<UserLoginDetails,Integer> {

    public UserLoginDetails findByUsername(String username);
}
