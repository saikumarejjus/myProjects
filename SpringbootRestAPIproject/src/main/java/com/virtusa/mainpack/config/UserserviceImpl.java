package com.virtusa.mainpack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtusa.mainpack.controller.Admincontroller;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.exception.UserNotfoundexception;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;

@Service
public class UserserviceImpl implements UserDetailsService {

	

	@Autowired
	AdminlogDetailsrepo admlogserv;

	//to load the user details from the database
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Userlogdetails admdtls = admlogserv.findByUsername(username);
		
		//checks if user details are null
		if(admdtls==null)
		{
			Admincontroller.log.info("user not found");
			throw new UserNotfoundexception("user not found");
			
		}
		return new UserdetailsImplementation(admdtls);
	}

}
