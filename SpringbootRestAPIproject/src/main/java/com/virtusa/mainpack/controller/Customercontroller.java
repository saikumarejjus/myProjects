package com.virtusa.mainpack.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.virtusa.mainpack.entity.CustomerAddres;
import com.virtusa.mainpack.entity.Customercart;
import com.virtusa.mainpack.entity.CustomerorderDetails;
import com.virtusa.mainpack.entity.Customerorderstatus;
import com.virtusa.mainpack.entity.Jsontokenmsg;
import com.virtusa.mainpack.entity.OrderProductsforStore;
import com.virtusa.mainpack.entity.Productdetails;
import com.virtusa.mainpack.entity.Stockalertforcustomer;
import com.virtusa.mainpack.entity.Userlogdetails;
import com.virtusa.mainpack.repository.AdminlogDetailsrepo;
import com.virtusa.mainpack.repository.CustomerAddresrepo;
import com.virtusa.mainpack.repository.Customercartrepo;
import com.virtusa.mainpack.repository.CustomerorderDetailsrepo;
import com.virtusa.mainpack.repository.Customerorderstatusrepo;
import com.virtusa.mainpack.repository.Productdetailsrepo;
import com.virtusa.mainpack.repository.Stockalertforcustomerrepo;


@RestController
@RequestMapping("/customer")
public class Customercontroller {

	
	@Autowired
	Productdetailsrepo prodserv;
	
	@Autowired
	AdminlogDetailsrepo custlogrepo;

	@Autowired
	CustomerorderDetailsrepo custordrepo;
	
	@Autowired
	Admincontroller admcontroobj;
	
	@Autowired
	Customercartrepo custcartrepo;
	
	@Autowired
	CustomerAddresrepo custadrrepo;
	
	@Autowired
	Stockalertforcustomerrepo stockalertrepo;
	
	private static int orderidno=20101;
	
	@PostMapping("/addtocart")
	public EntityModel<Object> addtocustomercar(@Valid @RequestBody List<Customercart> objlist)
	{
		//to get all the product names in the store
		List<Productdetails> availobj= prodserv.findAll();
		
		HashMap<String, Integer> prodnamePrice =   (HashMap<String, Integer>) availobj.stream().collect(Collectors.toMap(Productdetails::getProductname, Productdetails::getProductsavalible));  
		 
		String[] added = {""};
		String[] notadded = {""};
		String[] notinStock = {""};

		
		
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).seeproductsincart()).withRel("View  product items in cart");
		//iterating and checks whether ordered product name exist in the store or not
        objlist.stream().forEach( obj -> {
        	
			String pname=obj.getProdname();
			if(prodnamePrice.containsKey(pname))
			{
				if(prodnamePrice.get(pname)>=obj.getNoquant())
				{
					//adding  items to cart 
					custcartrepo.save(obj);
					added[0] =added[0]+ obj.getProdname()+", ";
				}
				else
				{
					Stockalertforcustomer stockalert = new Stockalertforcustomer(obj.getUsername() , obj.getProdname() , obj.getNoquant());
					stockalertrepo.save(stockalert);
					notinStock[0] =notinStock[0]+ obj.getProdname()+", ";
				}
			   
		   }
		  else
			{
			  notadded[0] =notadded[0]+ obj.getProdname()+", ";
			}
		});
		
		if(notadded[0].isEmpty() && notinStock[0].isEmpty())
		{
			 String msg = "All products are added to cart succesfully ";			
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
		     entityModel.add(link1);
		     return entityModel;			
		}
		else
		{
			 String msg = "added products are : "+added+" and products not added are : <<"+notadded+">>beacuse these product names doesn't exists in store  and << "+notinStock+" >> are not added in the cart becuase the products are out of stock" ;
			 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
		     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);	
		     entityModel.add(link1);
			return entityModel;
		}
	}
	
	@GetMapping("/addtocart")
	public List<Customercart> seeproductsincart()
	{
		return custcartrepo.findByUsername(Logincontroller.getUsername());
	}
	
	@DeleteMapping("/addtocart/{productname}")
	public EntityModel<Object> removeproducts(@Valid @PathVariable("productname") String productname )
	{
		List<Customercart> object= custcartrepo.findByUsername(Logincontroller.getUsername());
        Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).seeproductsincart()).withRel("View  product items in cart");

		for(Customercart obj : object)
		{
        	if(obj.getProdname().equals(productname))
	        {    
        		custcartrepo.deleteById(obj.getProductcartno());
        		 String msg = " item is deleted from cart successfully" ;
				 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
			        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
			        entityModel.add(link1);
					return entityModel;
					
	        }
					
			else
			{
				 String msg = "entered productname is does not exists in the cart";
				 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
			        EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
			        entityModel.add(link1);
					return entityModel;
			}
		}
		return null;
	}
	
	@PostMapping("/orderproducts")
	public EntityModel<Object> orderitemsincart(@RequestBody CustomerAddres custord)
	{
		List<Customercart> cartlist = custcartrepo.findByUsername(Logincontroller.getUsername());
		EntityModel<Object> entityModel = customerinput(cartlist ,custord);
		return entityModel;
	}
	
	@Autowired
	Customerorderstatusrepo custordstatusrepo;
	
	//This method allows customer to purchase products from store
	public EntityModel<Object> customerinput( List<Customercart> objlist ,CustomerAddres custord)
	{
		List<CustomerorderDetails> ordtlist = new ArrayList<>();
		//iterating and checks whether ordered product name exist in the store or not
			
			objlist.stream().forEach( obj ->  {
							  //all customer order details according to customer order input
								Productdetails pdobj = prodserv.findByProductname(obj.getProdname());
							
								int prodprice = pdobj.getProductprice();
								CustomerorderDetails custordobj =  new CustomerorderDetails();
								custordobj.setOrderid(getOrderidno());
								custordobj.setCustomername(obj.getUsername());
								custordobj.setPoductname(obj.getProdname());
								custordobj.setPoductprice(prodprice);
								custordobj.setPoductquanity(obj.getNoquant());
								custordobj.setTotalordervalue(prodprice*obj.getNoquant());
								
								ordtlist.add(custordobj); 
								
								Customerorderstatus ordstatus = new Customerorderstatus(getOrderidno() ,"NOT_DELIVERED");
								custordstatusrepo.save(ordstatus);
								
								custcartrepo.deleteById(obj.getProductcartno());
								
						     	} );
		
		custord.setCustord(ordtlist);
		custadrrepo.save(custord);
		 int ordid= getOrderidno();
		 List<CustomerorderDetails> custobj = custordrepo.findByOrderid(ordid);
		 Userlogdetails emobj = custlogrepo.findByUsername(Logincontroller.getUsername());
		 Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).showyourorders(ordid)).withRel("View  your ordered details by orderid");
		 setOrderidno(getOrderidno()+1);
		 String msg = "All products are ordered succesfully and and your order id no is : "+ordid;
		
		 Jsontokenmsg jwtResponse = new Jsontokenmsg(msg);
	     EntityModel<Object> entityModel = EntityModel.of(jwtResponse);
	     entityModel.add(link1);
			
		 String msg2 = custobj+"All products are ordered succesfully and and your order id no is : "+ordid;
	     admcontroobj.sendmail(emobj.getUsermail() ,msg2 ," your order bill" );
	     
		 return entityModel;			
		 
	}
	


	//This method shows what products purchased by customer
	@GetMapping("/orderproducts")
	public List<CustomerorderDetails> showyourorders()
	{
	
			return custordrepo.findByCustomername(Logincontroller.getUsername());
		
	}
	
	//This method is to show the ordered product by order id
	@GetMapping("/orderproducts/{orderid}")
	public List<CustomerorderDetails> showyourorders(@PathVariable("orderid") int orderid)
	{
		 List<CustomerorderDetails> obj = custordrepo.findByOrderid(orderid);
		 return obj;
	}
	

	//This method is to show customer login details
	@GetMapping("/showlogindetails")
	public Userlogdetails  showcustlogindtls()
	{
		return custlogrepo.findByUsername(Logincontroller.getUsername());	
	}
	
	@Autowired
	BCryptPasswordEncoder bysc;
	
	//This method is to update customer login password 
	@PutMapping("/showlogindetails/{newpswd}")
	public ResponseEntity<Object>  showcustlogindtls(@PathVariable("newpswd") String newpassword)
	{
		Userlogdetails updateobj = custlogrepo.findByUsername(Logincontroller.getUsername());
		
		//checking whether the new password and old password are matching
		if(newpassword.equals(updateobj.getUserpassword()))
		{
			 return ResponseEntity.ok(new Jsontokenmsg(" the entered new password is same as old password ... try with another password"));
		}
		else
		{
			//updating encrypted password
			updateobj.setUserpassword(bysc.encode(newpassword));
			custlogrepo.save(updateobj);
			URI loc = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/customer/showlogindetails")
					  .build().toUri();
			return ResponseEntity.created(loc).build();
		}
	}
	
	
	//This method is to generate the bill  for order given by administrator 
		@GetMapping("/generateorderbill/{orderid}")
		public ResponseEntity<Object> orderbill(@PathVariable("orderid") int orderid)
		{
			 List<CustomerorderDetails> ordobj = custordrepo.findByOrderid(orderid);
			 int[] bill = {0};
			 ordobj.stream().forEach(p -> bill[0] =bill[0]+ p.getTotalordervalue());
		  
		     Userlogdetails emobj = custlogrepo.findByUsername(Logincontroller.getUsername()); 
			 String  msg=  getbillbyorderid(ordobj , emobj);
			 msg=msg+"\n"+"Total order price : "+bill[0];	
			 admcontroobj.sendmail(emobj.getUsermail() ,msg," the order bill"  );
			     
			return ResponseEntity.ok(msg);
		  
		}
		
		@DeleteMapping("/showadmlogdtls")
		public  ResponseEntity<Jsontokenmsg> deleteadmin()
		{
			custlogrepo.deleteById(Logincontroller.getUsername());
			return ResponseEntity.ok(new Jsontokenmsg("admin account deleted succusfully"));
		}

	public static int getOrderidno() {
		return orderidno;
	}

	public static void setOrderidno(int orderidno) {
		Customercontroller.orderidno = orderidno;
	}
	
	 public static String getbillbyorderid(List<CustomerorderDetails> orderobj, Userlogdetails emobj) 
	    {	           
		      String temp=""; 	      
		      try {	       	         
			          temp=temp+"Product Id: "+emobj.getUsername()+"\n";
			          temp=temp+"product count: "+emobj.getFullname()+"\n";
			          temp=temp+"product cost: "+emobj.getUserphno()+"\n";
			          temp=temp+"seller email: "+emobj.getUsermail()+"\n";		        
			          temp=temp+String.format("%n%-15s%-15s%-15s%-15s%-15s%-15s%%n%n", "order Id", "Product name" , "product count","product cost", "seller email", "seller name","total order cost" , "order date");		         
			          for(CustomerorderDetails obj:orderobj)
			          {
			              temp=temp+String.format("%-15s%-15s%-15s%-15s%-15s%-15s%%n" ,obj.getOrderid(), obj.getPoductname(),obj.getPoductprice(), obj.getPoductquanity(),obj.getTotalordervalue(),obj.getOrderdate() );		              
			          }
			                 		              		         
		         }catch(Exception e) {
		              return "no orederid found";
	             }  	 
		      return temp;	
	    }
	
	
	
	  
 }



	
	
	
	

