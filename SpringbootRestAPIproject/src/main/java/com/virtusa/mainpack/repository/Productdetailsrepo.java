package com.virtusa.mainpack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.Productdetails;

@Repository
public interface Productdetailsrepo extends JpaRepository<Productdetails, Integer> {

	@Query("select productid,productname,productprice from Productdetails")
	public List<Productdetails> getproddetls();
	
	@Query("select productprice from Productdetails where productname=?1")
	public int getprodprice(String productname);

	public Productdetails findByProductid(int id);

	public Productdetails findByProductname(String pname);
	
}
