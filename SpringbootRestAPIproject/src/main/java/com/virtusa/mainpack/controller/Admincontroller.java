package com.virtusa.mainpack.controller;


import java.io.FileWriter;
import java.net.URI;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.virtusa.mainpack.entity.CustomerorderDetails;
import com.virtusa.mainpack.entity.Customerorderstatus;
import com.virtusa.mainpack.entity.Jsontokenmsg;
import com.virtusa.mainpack.entity.Logsuthdtls;
import com.virtusa.mainpack.entity.OrderProductsforStore;
import com.virtusa.mainpack.entity.Productdetails;
import com.virtusa.mainpack.entity.Stockalertforcustomer;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.exception.ProductNotFoundException;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;
import com.virtusa.mainpack.repository.CustomerorderDetailsrepo;
import com.virtusa.mainpack.repository.Customerorderstatusrepo;
import com.virtusa.mainpack.repository.Orderproductsforstorerepo;
import com.virtusa.mainpack.repository.Productdetailsrepo;
import com.virtusa.mainpack.repository.Stockalertforcustomerrepo;

@RestController
@RequestMapping("/admin")
public class Admincontroller {

	 public static final Logger log = LogManager.getLogger(Admincontroller.class.getName());  
	 
	
	@Autowired
	Orderproductsforstorerepo orderprodserv;
	
	@Autowired
	Logsuthdtls msgobj;
	
	@Autowired
	AdminlogDetailsrepo admlogserv;
	
	@Autowired
	CustomerorderDetailsrepo custordrepo;
	
	@Autowired
	Productdetailsrepo prodserv;
	
	private static int orderidno=10101;
	
	//This method is to display all the available products and their details in the store
	@GetMapping("/prouctsdetails")
	@Transactional(readOnly=true)
	@Cacheable("admcuscache")
	public List<Productdetails> showproductdetailscontro()
	{
		return prodserv.findAll();
	
	}
	

	//This method is to display all the available products and their details in the store by product id 
	@GetMapping("/prouctsdetails/{productid}")
	@Transactional(readOnly=true)
	@Cacheable("admcuscache")
	public Optional<Productdetails> showproductdetailscontrobyid(@Valid @PathVariable("productid") int productid)
	{
		
		return prodserv.findById(productid);
	}
	

	//This method is to add new products in the store 
	@PostMapping("/prouctsdetails")
	public EntityModel<Object> addproductsintostorecontro(@Valid @RequestBody List<Productdetails> prodlist)
	{
		
		List<Productdetails> availobj= prodserv.findAll();
		String[] added = {""};
		String[] notadded = {""};
						
		//iterating the list of user input product details
		prodlist.stream().forEach( prod ->{  
		//to get product ids in store
						List<Integer> prodids =availobj.stream().map(Productdetails::getProductid).collect(Collectors.toList());
						
						//checks whether the product exists or not
						if(!prodids.contains(prod.getProductid()))
							{	
								prodserv.save(prod);
								added[0] =added[0]+ prod.getProductid()+", ";
											
							}
							else
							{			
								notadded[0] =notadded[0]+ prod.getProductid()+", ";
							}
						});
										
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).showproductdetailscontro()).withRel("View  product details");
           
	   //to display number of  records are inserted succesfully
		if(notadded[0].isEmpty())
		{
			String msg = "all products are added successfully" ;
			Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
	        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
	        entityModel.add(link1);
			return entityModel;						
		}
		else
		{
			 String msg= "added products are : "+added[0]+" and products not added are : "+notadded[0]+" beacuse these product ids are already exists in store" ;
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		        entityModel.add(link1);
				return entityModel;
		}
		 
	}
	

	//This method is to delete product  in the store by product id
	@DeleteMapping("/prouctsdetails/{productid}")
	public EntityModel<Object> removeproducts(@PathVariable("productid") int productid )
	{
		List<Productdetails> object= prodserv.findAll();
		List<Integer> prodids =object.stream().map(Productdetails::getProductid).collect(Collectors.toList());
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).showproductdetailscontro()).withRel("View  product  details");

		//check whether the  given product id present in the store
		if(prodids.contains(productid))
		{
			 prodserv.deleteById(productid);
			 String msg = " product is delete successfully" ;
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		        entityModel.add(link1);
				return entityModel;
		}
		else
		{
			 String msg = " productid is does not exists in the product store";
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		        entityModel.add(link1);
				return entityModel;
		}
	}
	

	//This method is to display all the order details which are ordered by administrator for store
	@GetMapping("/orderproucts")
	public List<OrderProductsforStore> adminorderedprocductscontro()
	{
		 return orderprodserv.findAll();
	}

	//This method is to display the order details of product  which are ordered by administrator for store by order id 	
	@GetMapping("/orderproucts/{orderid}")	
	public List<OrderProductsforStore> singleadminorderedprocductscontro(@PathVariable("orderid") int orderid)
	{
		return  orderprodserv.findByOrderid(orderid);
		 
	}
	

	//This method is to order product for the store by administrator for store
	@PostMapping("/orderproucts")
	public EntityModel<Object> orderproductstostorecontro(@Valid @RequestBody List<OrderProductsforStore> objlist) 
	{
		List<Productdetails> object= prodserv.findAll();
		
		//to get all product ids of products in the store
		List<Integer> prodids =object.stream().map( Productdetails::getProductid).collect(Collectors.toList());
		String[] added = {""};
		String[] notadded = {""};
		
		objlist.stream().forEach( obje -> {
			//checking if product id exists or not
		   if(prodids.contains(obje.getProductid()))
			  {
			   
			   //update the total order value of this order
			    obje.setOrderid(getOrderidno());
				obje.setTotalordercost(obje.getProductcost()*obje.getProductcount());
				OrderProductsforStore robj2 = orderprodserv.save(obje);
				added[0] =added[0]+ obje.getProductid()+", ";
				
				customeralertforstock(obje);
				
				List<Productdetails> listproducts =	object.stream().filter(p -> p.getProductid()==robj2.getProductid()).collect(Collectors.toList());
				listproducts.stream().forEach( p -> { p.setProductsavalible(robj2.getProductcount()+p.getProductsavalible());
														prodserv.save(p);
														});
				
			  }
			else
			   {
				  notadded[0] =notadded[0]+ obje.getProductid()+", ";
								   }
						
		});
		int ordid= getOrderidno();
		setOrderidno(getOrderidno()+1);
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).adminorderedprocductscontro()).withRel("View all admin ordered details");
        Link link2 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).orderbill(ordid)).withRel("get total order cost details");
        
        Userlogdetails main =  admlogserv.findByRoles_Roleid(3);
        List<OrderProductsforStore> ordobj= orderprodserv.findByOrderid(ordid);
		//to check all orders are place succesfully
		if(notadded[0].isEmpty())
		{
			 String msg = "All products are ordered succesfully  \n  your order id no is : "+ordid ;
			Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		        entityModel.add(link1);
		        entityModel.add(link2);
				 String msg2 = ordobj+"All products are ordered succesfully  \n  your order id no is : "+ordid ;
				 sendmail(main.getUsermail() ,msg2  ," your orders bill" );
				return entityModel;
			
		}
		else
		{
			 String msg = "ordered products are : "+added+" and products not ordered are : "+notadded+" beacuse these product ids are does not exists in store and  your order id no is : "+ordid;
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		        entityModel.add(link1);
		        entityModel.add(link2);
				 String msg2 = ordobj+"ordered products are : "+added+" and products not ordered are : "+notadded+" beacuse these product ids are does not exists in store and  your order id no is : "+ordid;

		        sendmail(main.getUsermail() ,msg2  ," your orders bill" );
				return entityModel;
			
		}
		
		
		 
	}
	
	@Autowired
	Stockalertforcustomerrepo stockalertrepo;
	
	public void customeralertforstock(OrderProductsforStore ordobj)
	{
		Productdetails product= prodserv.findByProductid(ordobj.getProductid());

		
		List<Stockalertforcustomer> alertobj = stockalertrepo.findByProdname(product.getProductname());
		List<String> custunames  = alertobj.stream().map( Stockalertforcustomer::getCustuname).collect(Collectors.toList());
		
		custunames.stream().forEach( p -> {
											Userlogdetails main =  admlogserv.findByUsername(p);
											String msg2 = "good news !!! "+product.getProductname()+" is now available in stock. Hurry Up to purchase product before stock completes ...";
											sendmail(main.getUsermail() ,msg2  ," products available" );
											
											}); 
		
	}
	

	//This method is to display login details of the administrator 
	@GetMapping("/showadmlogdtls")
	
	public Userlogdetails adminlogetailscontro()
	{
		return  admlogserv.findByUsername(Logincontroller.getUsername());
		
	}
	
	
	
	@Autowired
	BCryptPasswordEncoder bysc;
	
	//This method is to update administrator login password of the administrator
	@PutMapping("/showadmlogdtls/{password}")
	public ResponseEntity<Object> updateadminlogdetailscontro( @PathVariable("password") String password) 
	{
		
			// to find whether the admin id present in the databse
			Userlogdetails updateobj = admlogserv.findByUsername(Logincontroller.getUsername());
			if(password.equals(updateobj.getUserpassword()))
			{
				 return ResponseEntity.ok(new Jsontokenmsg(" the entered new oassword is same as old password ... try with another password"));
			}
			else
			{
			updateobj.setUserpassword(bysc.encode(password));
			admlogserv.save(updateobj);
			
			//to return url after updating password
			URI loc = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/admin/showadmlogdtls")
					  .build().toUri();
			return ResponseEntity.created(loc).build();
			}
	}
	
	@DeleteMapping("/showadmlogdtls")
	public  ResponseEntity<Jsontokenmsg> deleteadmin()
	{
		admlogserv.deleteById(Logincontroller.getUsername());
		return ResponseEntity.ok(new Jsontokenmsg("admin account deleted succusfully"));
	}
	
	

	//This method is to update the product price of the product in the store by Administrator
	@PutMapping("/prouctsdetails/{productid}/{newprice}")
	public ResponseEntity<Jsontokenmsg> changeprodprice(@PathVariable("productid")int productid, @PathVariable("newprice") int newprice)
	{
		 Productdetails obj= prodserv.findByProductid(productid);
		//checks if product id presents or not
		if(obj!=null)
		{			
				obj.setProductprice(newprice);
				prodserv.save(obj);
				URI loc = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/admin/prouctsdetails/{productid}")
			    		  .buildAndExpand(obj.getProductid()).toUri();
				return ResponseEntity.created(loc).build();	
		}
		else
		{
			 throw new ProductNotFoundException(" the entered product id does not exists in store");
		}
	
	}
	
	public  int  totalorderbill =  0;
			
	//This method is to generate the bill  for order given by administrator 
	@GetMapping("/generateorderbill/{orderid}")
	public ResponseEntity<Object> orderbill(@PathVariable("orderid") int orderid) 
	{
		 List<OrderProductsforStore> ordobj= orderprodserv.findByOrderid(orderid);
		int[] bill = {0};
		//iterating all the orders
		ordobj.stream().forEach( p -> bill[0] =bill[0]+ p.getTotalordercost());
		
		totalorderbill = bill[0];	
		Userlogdetails emobj = admlogserv.findByUsername(Logincontroller.getUsername());	     
	    String  msg=  getbillbyorderid(ordobj , emobj);
	    
	     sendmail(emobj.getUsermail() ,msg," the order bill"  );
	     
		return ResponseEntity.ok(msg);
	}


	
	//This method is to generate the bill for the whole year for orders given by administrator 
	@GetMapping("/generatebill/{year}")
	public ResponseEntity<Object> yearbill(@PathVariable("year") int year)
	{
		List<OrderProductsforStore> ordobj= orderprodserv.findAll();
		 Set<Integer> orderids = new HashSet<>();
		 int[] bill = {0};
		//iterating all the products
		
		ordobj.stream().forEach( obj -> {
			//to get year from  product ordered date
			Calendar cal = Calendar.getInstance();
			cal.setTime(obj.getDate());
			int yr= cal.get(Calendar.YEAR);
			
			//checking all the products that are ordered on the given year
			if(year == yr)
			{
				orderids.add(obj.getOrderid());
				bill[0] =bill[0]+ obj.getTotalordercost();	
			}			
		});
		totalorderbill = bill[0];
		Userlogdetails emobj = admlogserv.findByUsername(Logincontroller.getUsername());	     
	    String  msg=  getbillbyorderid(ordobj , emobj);
	    
	     sendmail(emobj.getUsermail() ,msg," the order bill"  );
	     
		return ResponseEntity.ok(msg);
   
	}
	
	//This method is to generate the bill for the current month for orders given by administrator
	@GetMapping("/generatebill/{year}/{month}")
	public ResponseEntity<Object> yearbill(@PathVariable("year") int year, @PathVariable("month") int month)
	{
		List<OrderProductsforStore> ordobj= orderprodserv.findAll();
		 Set<Integer> orderids = new HashSet<>();
		 int[] bill = {0};
		
		 ordobj.stream().forEach( obj -> {
			//to get year  and month from  product ordered date
			Calendar cal = Calendar.getInstance();
			cal.setTime(obj.getDate());
			int yr= cal.get(Calendar.YEAR);
			int mon = cal.get(Calendar.MONTH);
			
			//checking all the products that are ordered on the given year and month
			if(year == yr && month == mon+1)
			{
				orderids.add(obj.getOrderid());
				bill[0] =bill[0]+ obj.getTotalordercost();		
			}	
		 });
		 totalorderbill = bill[0];
		 Userlogdetails emobj = admlogserv.findByUsername(Logincontroller.getUsername());	     
		    String  msg=  getbillbyorderid(ordobj , emobj);
		    
		     sendmail(emobj.getUsermail() ,msg," the order bill"  );
		     
			return ResponseEntity.ok(msg);
	}
	
	//This method is to generate the bill for the current month for orders given by customer
		@GetMapping("/generatecustomerbill/{year}/{month}")
		public ResponseEntity<String> customermonthlybill(@PathVariable("year") int year, @PathVariable("month") int month)
		{
			List<CustomerorderDetails> ordobj= custordrepo.findAll();
			 Set<Integer> orderids = new HashSet<>();
			 int[] bill = {0};
				
			 ordobj.stream().forEach( obj -> {
				//to get year  and month from  product ordered date
				Calendar cal = Calendar.getInstance();
				cal.setTime(obj.getOrderdate());
				int yr= cal.get(Calendar.YEAR);
				int mon = cal.get(Calendar.MONTH);
				
				//checking all the products that are ordered on the given year and month
				if(year == yr && month == mon+1)
				{
					orderids.add(obj.getOrderid());
					bill[0] =bill[0]+ obj.getTotalordervalue();		
				}	
			 });
			 totalorderbill = bill[0];
			 Userlogdetails emobj = admlogserv.findByUsername(Logincontroller.getUsername());	     
			 String  msg=  Customercontroller.getbillbyorderid(ordobj , emobj);
			 msg=msg+"\n"+"Total order price : "+totalorderbill;	
			 sendmail(emobj.getUsermail() ,msg," the order bill"  );
			     
				return ResponseEntity.ok(msg);
		}
	
	//This method is show all customer order for the administrator
	@GetMapping("/customerorders")
	@Transactional(readOnly=true)
	@Cacheable("admcuscache")
	public List<CustomerorderDetails> seecustomeroreders()
	{
		return custordrepo.findAll();
	}
	
	@Autowired
	Customerorderstatusrepo custordstatusrepo;
	
	@PostMapping("/customerorders/{orderid}/{ordstatus}")
	public  ResponseEntity<Jsontokenmsg> setcustomerorederstaus(@Valid @PathVariable("orderid") int orderid,@Valid  @PathVariable("ordstatus") String ordstatus)
	{
		Customerorderstatus cust = custordstatusrepo.findByOrderid(orderid);
		if(cust!= null)
		{
			cust.setOrderstatus(ordstatus);
			custordstatusrepo.save(cust);
			return ResponseEntity.ok(new Jsontokenmsg("order is status updated..."));
		}
		else
		{
			return ResponseEntity.ok(new Jsontokenmsg("Oops!!! the orderid : "+orderid+"  is not found in orderslist !!!!"));
		}
		
	}


	public static int getOrderidno() {
		return orderidno;
	}


	public static void setOrderidno(int orderidno) {
		Admincontroller.orderidno = orderidno;
	}
	
	@Autowired
	private JavaMailSender javamailsender;
	
	public void  sendmail( String toEmail, String body , String subject ) 
	{
		MimeMessage mimeMessage = javamailsender.createMimeMessage();
		MimeMessageHelper mimeMessagehelper = new MimeMessageHelper(mimeMessage );
		try {
			mimeMessagehelper.setFrom("saikumarejjus@gmail.com");
			mimeMessagehelper.setTo(toEmail);
			mimeMessagehelper.setText(body);
			mimeMessagehelper.setSubject(subject);
			
			javamailsender.send(mimeMessage);
		} catch (MessagingException e) {
			log.info("message not sent");
			e.printStackTrace();
		}
		
	}
	
	//@RequestMapping("getTotalBill")
    public  String getbillbyorderid(List<OrderProductsforStore> orderobj, Userlogdetails emobj) 
    {	           
	      String temp=""; 	      
	      try {	       	         
		          temp=temp+"Product Id: "+emobj.getUsername()+"\n";
		          temp=temp+"product count: "+emobj.getFullname()+"\n";
		          temp=temp+"product cost: "+emobj.getUserphno()+"\n";
		          temp=temp+"seller email: "+emobj.getUsermail()+"\n";		        
		          temp=temp+String.format("%n%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n%n", "Order Id", "Product Id", "product count","product cost", "seller email", "seller name","total order cost" , "order date");		         
		          for(OrderProductsforStore obj:orderobj)
		          {
		              temp=temp+String.format("%-15s%-15s%-5s%-15s%-15s%-15s%-15s%-15s%n",obj.getOrderid(),obj.getProductid(), obj.getProductcost(),obj.getProductcost(), obj.getSelleremail(),obj.getSellername(),obj.getTotalordercost() ,obj.getDate());		              
		          }
		          temp=temp+"\n"+"Total order price : "+totalorderbill;	        		              		         
	         }catch(Exception e) {
	              return "no orederid found";
             }  	 
	      return temp;	
    }
	
	
	

    	
}
