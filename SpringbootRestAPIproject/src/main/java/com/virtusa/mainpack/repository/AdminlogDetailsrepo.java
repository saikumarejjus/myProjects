package com.virtusa.mainpack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.Userlogdetails;

@Repository
public interface AdminlogDetailsrepo extends JpaRepository<Userlogdetails, String> {


	public Userlogdetails findByUsername(String username);

	public Userlogdetails findByRoles_Roleid(int roleid);
	 
	
}
