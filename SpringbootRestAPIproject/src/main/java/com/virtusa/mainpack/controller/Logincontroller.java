package com.virtusa.mainpack.controller;


 
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.virtusa.mainpack.config.JwtUtil;
import com.virtusa.mainpack.config.UserserviceImpl;
import com.virtusa.mainpack.entity.Admrole;
import com.virtusa.mainpack.entity.Jsontokenmsg;
import com.virtusa.mainpack.entity.Logsuthdtls;
import com.virtusa.mainpack.entity.Productdetails;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.exception.InvaidDetails;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;
import com.virtusa.mainpack.repository.Productdetailsrepo;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/home")
public class Logincontroller {

	//creating logger object
	 public static final Logger logger = LogManager.getLogger(Logincontroller.class.getName()); 

	// to assign to assign the login user name to this varible beacuse this is in another method of other class
	private static String username;
	
	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Logincontroller.username = username;
	}
	
	@Autowired
	UserserviceImpl userserv;
	
	@Autowired
	JwtUtil jutil;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Autowired
	Admincontroller admobj;
	
	//This method is to generate the the token for accessing url which need to signin
	@PostMapping("/token")
    public EntityModel<Object> createToken(@Valid @RequestBody Logsuthdtls request) 
    {
        logger.info(request);
        
        //authenticating user according to user provide details
        try {
        authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        }
        
        catch(BadRequest e)
        {
            e.printStackTrace();
            throw new InvaidDetails("Bad Credentials");
        }
        setUsername(request.getUsername());
        Admincontroller.log.info(username);
        UserDetails udservice = userserv.loadUserByUsername(request.getUsername());
        System.out.println(udservice);
        String token =  jutil.generateToken(udservice);
        logger.info(token);
        
        Jsontokenmsg jwtResponse = new Jsontokenmsg(token);
        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(Admincontroller.class).showproductdetailscontro()).withRel("View  product details");
        
        entityModel.add(link1);
        return entityModel;
       
        
    }
	
	
	
	
	
	@Autowired
	AdminlogDetailsrepo custlogrepo;
	
	@Autowired
	BCryptPasswordEncoder bysc;
	
	@Autowired
	Logsuthdtls logobj;
	
	@Autowired
	Admincontroller admcontroobj;
	
	Userlogdetails storeobj = null;
	int otpgen =0;
	//This method is to register new customer for the first time
	@PostMapping("newcustomersignup")
	public EntityModel<Object> newcustomercontro(@Valid @RequestBody Userlogdetails obj)
	{
		//to get all usernames of login details
		List<Userlogdetails> unamelist= custlogrepo.findAll();
		List<String> custnames =unamelist.stream().map(Userlogdetails::getUsername).collect(Collectors.toList());
		List<Userlogdetails> maillist= custlogrepo.findAll();
		List<String> custmails =maillist.stream().map(p -> p.getUsermail()).collect(Collectors.toList());
		
		//checking if user name previously exist or not
		if(custnames.contains(obj.getUsername()))
		{
			String msg = " customer username allready  exists , try with another username ";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		     return entityModel;
		}
		else if (custmails.contains(obj.getUsermail()))
		{
			String msg = " customer user mail allready  exists , try with another mail ";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		     return entityModel;
		}
		else
		{
			
			String msg = " Enter otp which is sent to your mail";
			storeobj = obj;
			
			 otpgen = generateotp();
			String msg1  =otpgen+ " your otp for gmail verification";
			admcontroobj.sendmail(obj.getUsermail()  ,msg1 , " one time password ");
						
			 Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).otpvalidationmethod(0)).withRel("to enter otp as path variable and replcae <<0>> with otp");
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		     entityModel.add(link1);
		     return entityModel;     
			 
		}
		
	}
	
	Random rand = new Random();  
	public int generateotp()
	{
		int low = 100000;
		int high = 999999;
		int seotp = rand.nextInt(high-low) + low;
		return seotp;
	}
	
	@PostMapping("/sendotp/{otp}")
	public EntityModel<Object> otpvalidationmethod(@Valid @PathVariable("otp") int otp)
	{
		
		if(otp == otpgen)
		{
			
			String msg = savenewuser();
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);		   
		     return entityModel;
		}
		else
		{
			String msg = "enter otp is wrong and re-enter valid otp";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		     return entityModel;
		}
		
	}
	
	public String savenewuser()
	{
		setUsername(storeobj.getUsername());
		
		storeobj.setUserpassword(bysc.encode(storeobj.getUserpassword()));
		storeobj.setRoles(new Admrole(2,"ROLE_CUSTOMER"));
		 custlogrepo.save(storeobj); 
	     return  " customer account created succuesfully";
	}
	
	@PostMapping("/forgotpassword/{username}")
	public EntityModel<Object> forgotuserpassword(@Valid @PathVariable("username") String username)
	{
		 Userlogdetails emobj = custlogrepo.findByUsername(username);
		 storeobj = emobj;
		 
		 String msg = " Enter otp which is sent to your mail";			
		 otpgen = generateotp();
		String msg1  =otpgen+ " your otp for gmail verification";
		admcontroobj.sendmail(emobj.getUsermail()  ,msg1 , " one time password ");
		
		 Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).otpvalidationmethod(0)).withRel("to enter otp as path variable and replcae <<0>> with otp");
		 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
	     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
	     entityModel.add(link1);
	     return entityModel; 
	}
	
	@PostMapping("/forgotpassword/{otp}/{newpswd}")
	public EntityModel<Object> updateforgottenpassword(@PathVariable("otp") int otp, @Valid @PathVariable("newpswd") String newpswd )
	{
		if(otp== otpgen)
		{
			storeobj.setUserpassword(bysc.encode(newpswd));
			custlogrepo.save(storeobj);
			String msg = " your password is updated succesfully ";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		    
		     return entityModel;
		}
		else
		{
			String msg = "enter otp is wrong and re-enter valid otp";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		    
		     return entityModel;
		}
	}
	
	@Autowired
	Productdetailsrepo prodserv;
	
	//This method is show the product details present  in store to the customer
	@GetMapping("/prouctsdetails")
	public CollectionModel<Productdetails> filterproddetails()
	{
		
		List<Productdetails> prodlst = prodserv.findAll();
		CollectionModel<Productdetails> entityModel = CollectionModel.of(prodlst);
		 Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).filterproddetails()).withRel("View  products details");

	        entityModel.add(link1);
	     
	        return entityModel;
	}
	



	
	
	
}
