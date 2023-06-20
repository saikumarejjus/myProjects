package com.virtusa.mainpack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.mainpack.entity.CustomerAddres;

@Repository
public interface CustomerAddresrepo extends JpaRepository<CustomerAddres, Integer> {

}
