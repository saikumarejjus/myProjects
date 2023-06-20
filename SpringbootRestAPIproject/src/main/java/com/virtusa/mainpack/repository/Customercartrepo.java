package com.virtusa.mainpack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.virtusa.mainpack.entity.Customercart;

@Repository
public interface Customercartrepo extends JpaRepository<Customercart, Integer> {

    public	List<Customercart> findByUsername(String username);

	public void deleteByProductcartno(int productcartno);

	

}
