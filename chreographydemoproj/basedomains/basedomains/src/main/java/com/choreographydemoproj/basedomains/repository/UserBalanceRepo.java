package com.choreographydemoproj.basedomains.repository;

import com.choreographydemoproj.basedomains.dto.UserBalance;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepo extends JpaRepository<UserBalance,Integer> {
}
