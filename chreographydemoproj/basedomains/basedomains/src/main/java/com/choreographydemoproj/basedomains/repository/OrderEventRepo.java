package com.choreographydemoproj.basedomains.repository;

import com.choreographydemoproj.basedomains.dto.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventRepo extends JpaRepository<OrderEvent,String> {
}
