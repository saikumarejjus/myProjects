package com.virtusa.mainpack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.CustomerorderDetails;

@Repository
public interface CustomerorderDetailsrepo extends JpaRepository<CustomerorderDetails, Integer> {

	public List<CustomerorderDetails> findByCustomername(String custname);

	public List<CustomerorderDetails> findByOrderid(int orderid);
}
