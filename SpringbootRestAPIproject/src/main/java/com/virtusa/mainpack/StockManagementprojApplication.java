package com.virtusa.mainpack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.virtusa.mainpack.entity.Admrole;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class StockManagementprojApplication implements CommandLineRunner {

public static void main(String[] args){
	SpringApplication.run(StockManagementprojApplication.class, args);
     }
   
	@Autowired
	AdminlogDetailsrepo admrepo;
	
	@Autowired
	Userlogdetails custrepo;
	
	@Autowired
	BCryptPasswordEncoder bysc;
	

	
	
	@Override
	public void run(String... args) throws Exception {
		
		Userlogdetails ad3 = new Userlogdetails();
		
		ad3.setFullname("surappadu");
		ad3.setUsername("mainadmin");
		ad3.setUserpassword(bysc.encode("SURAabc@321")); 
		ad3.setUsermail("saikumar.20010615@gmail.com");
		ad3.setRoles(new Admrole(3,"ROLE_MAINADMIN"));
		ad3.setUserphno("6300521761");
		admrepo.save(ad3);
		
		
		Userlogdetails ad1 = new Userlogdetails();
		
		ad1.setFullname("saikumarejjus");
		ad1.setUsername("saik");
		ad1.setUserpassword(bysc.encode("SAIabc@123"));
		ad1.setUsermail("saikumarejjus@gmail.com");
		ad1.setRoles(new Admrole(1,"ROLE_ADMIN"));
		ad1.setUserphno("9701613986");
		admrepo.save(ad1);
		
		
		Userlogdetails ad2 = new Userlogdetails();
		
		ad2.setFullname("saisiddu");
		ad2.setUsername("saigani");
		ad2.setUserpassword(bysc.encode("GANIsai@321"));
		ad2.setUsermail("saiumar.50601@gmail.com");
		ad2.setRoles(new Admrole(2,"ROLE_CUSTOMER"));
		ad2.setUserphno("9701613986");
		admrepo.save(ad2);
		
	
	}
}