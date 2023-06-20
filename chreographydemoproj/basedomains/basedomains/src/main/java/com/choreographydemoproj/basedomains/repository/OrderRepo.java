package com.choreographydemoproj.basedomains.repository;

 import com.choreographydemoproj.basedomains.dto.OrderDetails;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderDetails,String> {
}
