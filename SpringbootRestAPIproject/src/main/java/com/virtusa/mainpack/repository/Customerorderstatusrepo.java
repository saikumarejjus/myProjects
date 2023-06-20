package com.virtusa.mainpack.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.Customerorderstatus;

@Repository
public interface Customerorderstatusrepo extends JpaRepository<Customerorderstatus, Integer> {

	public Customerorderstatus findByOrderid(@Valid int orderid);

}
