package com.virtusa.mainpack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.Stockalertforcustomer;

@Repository
public interface Stockalertforcustomerrepo extends JpaRepository< Stockalertforcustomer, String> {

	public List<Stockalertforcustomer> findByProdname(String productname);

}
