package com.virtusa.mainpack.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.virtusa.mainpack.entity.Admrole;
import com.virtusa.mainpack.entity.Jsontokenmsg;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;

@RestController
@RequestMapping("/admincontroller")
public class Controlleradmin {
	
	

	@Autowired
	BCryptPasswordEncoder bysc;
	
	@Autowired
	AdminlogDetailsrepo custlogrepo;
	
	@PostMapping("showadmlogdtls")
	public EntityModel<Object> newcustomercontro(@Valid @RequestBody Userlogdetails obj)
	{
		//to get all user names of login details
		List<Userlogdetails> availobj= custlogrepo.findAll();
		List<String> custnames =availobj.stream().map(Userlogdetails::getUsername).collect(Collectors.toList());

		//checking if user name previously exist or not
			if(custnames.contains(obj.getUsername()))
			{
				String msg = " adminusername username allready  exists , try with another username ";
				 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
			     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
			     return entityModel;
			}
			else
			{
				Logincontroller.setUsername(obj.getUsername());
				obj.setUserpassword(bysc.encode(obj.getUserpassword()));
				obj.setRoles(new Admrole(1,"ROLE_ADMIN"));
				 custlogrepo.save(obj);
				 String msg = " admin account created succuesfully  ";
				
				 Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).adminlogetailscontro(obj.getUsername())).withRel("View  admin ordered details by order");
				 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
			     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
			     entityModel.add(link1);
			     return entityModel;
				
							 		 
			}	
	}
	
	
	@GetMapping("/showadmlogdtls/{username}")	
	public Userlogdetails adminlogetailscontro(@PathVariable("username") String username )
	{
		
		return  custlogrepo.findByUsername(username);
		
	}
	
	@GetMapping("/showadmlogdtls")
	@Transactional(readOnly=true)
	@Cacheable("admcuscache")
	public Userlogdetails adminlogetailscontro()
	{
		return  custlogrepo.findByUsername(Logincontroller.getUsername());
		
	}
	
	@PutMapping("/showadmlogdtls/{password}")
	public ResponseEntity<Object> updateadminlogdetailscontro( @PathVariable("password") String password) 
	{
		
			// to find whether the admin id present in the databse
			Userlogdetails updateobj = custlogrepo.findByUsername(Logincontroller.getUsername());
		
			updateobj.setUserpassword(bysc.encode(password));
			custlogrepo.save(updateobj);
			
			//to return url after updating password
			URI loc = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/admincontroller/showadmlogdtls")
					  .build().toUri();
			return ResponseEntity.created(loc).build();
			
	}

}
