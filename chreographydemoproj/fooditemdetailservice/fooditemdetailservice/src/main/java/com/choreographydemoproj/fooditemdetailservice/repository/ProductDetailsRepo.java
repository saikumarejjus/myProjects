package com.choreographydemoproj.fooditemdetailservice.repository;

import com.choreographydemoproj.fooditemdetailservice.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetails,Integer> {
}
