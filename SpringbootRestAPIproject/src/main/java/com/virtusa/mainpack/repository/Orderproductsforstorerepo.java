package com.virtusa.mainpack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.OrderProductsforStore;

@Repository
public interface Orderproductsforstorerepo extends JpaRepository<OrderProductsforStore, Integer> {

	public List<OrderProductsforStore> findByOrderid(int orderid);

}
