package com.choreographydemoproj.basedomains.repository;

import com.choreographydemoproj.basedomains.dto.UserTransaction;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepo extends JpaRepository<UserTransaction,Integer> {

    public UserTransaction findByOrderId(String OrderId);

}
