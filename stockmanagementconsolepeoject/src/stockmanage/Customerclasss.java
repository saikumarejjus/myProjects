package stockmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

public class Customerclasss {

	Scanner sccu = new Scanner(System.in);
	private static String fineusername="";
	RegExmethods regobj = new RegExmethods();

	public static void setFineusername(String fineusername) {
		Customerclasss.fineusername = fineusername;
	}
	
	//to verify the customer log details
 	public  void  userverification(Connection conn, Logger log) 
	{
		Mainclass clsobj1= new Mainclass();
		boolean cflag= true;
		//iterating for taking log details input if user enters incorrect details
		while(cflag) 
		{
				   String nuser= regobj.username();
					   
				   //assigning username  value to static variable
				   setFineusername(nuser);
				   String paswd= regobj.userpassword();
				   String q22 = "select customer_password from customer_login_details where customer_username='"+nuser+"'" ;
				   PreparedStatement pc2 = null;
				   ResultSet rs22;
				   String verifypas="";
				   // to initialize Statement connection
				try {
					pc2 = conn.prepareStatement(q22);
					 rs22 = pc2.executeQuery(q22);
					 
					   while(rs22.next())
					   {
						   verifypas = rs22.getString(1);
					   }
					} catch (SQLException e) {
				
						e.printStackTrace();
					}
				
				//closing statement connection
				finally
				{
					closeconection(pc2);
				}
				//verfying log details with database
				if(verifypas.equals(paswd))
			   	{
				
					 log.info("customer username and password is verified");
					 log.info("you haba been succesfull logged in");
					 cflag=false;
					 try {
						clsobj1.customerhome(conn);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				
			   	}
			   	else
			   	{
			   		log.info("enterd login details are not correct");
			   		cflag=true;
			   	}
		} 
	}
	
 	

 	//to see products details to customer
	public void seeproductss(Connection conn, Logger log) 
	{
		
		String aq1 = "select product_id, product_name , product_price  from product_details";
		Statement sta1 = null;
		ResultSet ars1;
		log.info(" product_id   \tproduct_name \t\tproduct_price \tproduct_avalible ");
		
		
		 // to initialize Statement connection
		try {
			sta1 = conn.createStatement();
			 ars1= sta1.executeQuery(aq1);
			 while(ars1.next())
				{
				 String str = ars1.getInt(1)+"\t \t\t"+ars1.getString(2)+"\t\t\t"+ars1.getInt(3);
				log.info(str);	
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		
		//closing statement connection
		finally
		{
			closeconection(sta1);
			
		}
		
		
			try {
				//calling customer home or logout method
				customerreturnhomelogout(conn,log);
			} catch (SQLException e) {
		
				e.printStackTrace();
			}
		
		
	}
 	
	//to get product price of particular product id
 	 public String getprodnameprice(Connection conn , int pid)
	 {
		 String qre1 = "select product_name from product_details where product_id='"+pid+"'";
	
	 	Statement str1 = null ;
	 	ResultSet rre;
	 	String val5 ="";
	 	
	 	 // to initialize Statement connection
	  try {
		  str1 = conn.createStatement();
		 rre = str1.executeQuery(qre1);
		 while(rre.next())
		 {
			 val5 = rre.getString(1);
			 
		 }
	 	} catch (SQLException e) {
		
	 		e.printStackTrace();
	 	}
	  
		//closing statement connection
	  finally
		{
		  closeconection(str1);
			
		}
	return val5;
	 }
 	 
 	 //to get customer phono for particular from the database
 	public long getcstnamephno(Connection conn )
	{
		   String aq22 = "select customer_phno from customer_login_details where customer_username='"+getFineusername()+"'";
			 Statement str2 = null  ;
			ResultSet rre2;
			long val3= 0;
			
			 // to initialize Statement connection
			try {
				
				str2 = conn.createStatement();
				rre2 = str2.executeQuery(aq22);
				while(rre2.next())
				{
					
					val3 = rre2.getLong(1);
				
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			//closing statement connection
			finally
			{
				closeconection(str2);
				
			}
			return val3;
	}
 	
 	//updating product available in the store after customer order
 	public void updateprodavailable(Connection conn, int pid , int pcount)
 	{
 		//to get product available before customer order
		 String qq = "select product_avalible from product_details where product_id='"+pid+"'";
		 Statement pcs = null;
		 ResultSet r;
		 int aval=0 ;
		 
		 // to initialize Statement connection
		try {
			pcs = conn.prepareStatement(qq);
			r = pcs.executeQuery(qq);
			 while(r.next())
			 {
				 aval = r.getInt(1);
				 
			 }
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		
		//closing statement connection
		finally
		{
			closeconection(pcs);
			
		}
		//updating product available in the store after customer order
		String qup = "update product_details set product_avalible=?-? where product_id=?";
		 PreparedStatement pc2 = null;
		 
		 // to initialize Statement connection
		try {
			pc2 = conn.prepareStatement(qup);
			 pc2.setInt(1, aval);
			 pc2.setInt(2, pcount);
			 pc2.setInt(3, pid);
			 pc2.executeUpdate();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			 closeconection(pc2);
		}
 	}
	
 	List<Integer> prodids = new ArrayList<>();
	public void getallproductsids(Connection conn) 
	{
		String aq1 = "select product_id from product_details";
		Statement sta1 = null  ;
		ResultSet ars1;
		
		//executing admin query
		try {
			
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				//inserting values to the products list
				prodids.add(ars1.getInt(1));
			}
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		}
	 
		//closing statement connection	
		finally
		{
			closeconection(sta1);
			
		}
		
}
 	//to take customer order
	public void customerorders(Connection conn, Logger log) 
	{
		boolean flg =  true;
		
		//iterating to take multiple orders from customer
		while(flg)
		{
			
			int pid;
			int pcount;
			pid = regobj.idee();	
			getallproductsids( conn);
			if(!prodids.contains(pid))
			{
				log.info(" the enterd product doesnot exists ...  enter vslid product id");
				continue;
			}
			else
			{
				pcount = regobj.prodcount();
				
				 	String val1=getFineusername();
				 	
				 	int val7 = pcount;
				 	
			 	
			 	//calling method get product price of current product
			 	String val5 =getprodnameprice( conn , pid);
			 	//calling method get phno of current customer
			 	Long val3 = getcstnamephno( conn );
			 	
			 	//calling method to updating product available in the store after customer order
				 updateprodavailable(conn,pid,pcount);
				
				String qrre3 = "insert into customer_order_details values (?,?,?,now(),?)";
				PreparedStatement prre3 = null;
				
				 // to initialize Statement connection
				try {
					prre3 = conn.prepareStatement(qrre3);
					prre3.setString(1, val1);						
					prre3.setLong(2, val3);
				
					prre3.setString(3, val5);
					prre3.setInt(4, val7);
					
						
				    prre3.executeUpdate();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				//closing statement connection
				finally
				{
					closeconection(prre3);
					
				}
			}
			
			

			log.info("If you want to oreder more products then enter <1> , else enter <0> : ");
			int  ad=sccu.nextInt();
			if(ad==0)
			{
				flg=false;
			}
		
			
		 }
		log.info("your order is placed succesfully !!!!! ");
		try {
			//calling customer home or logout method
			customerreturnhomelogout(conn,log);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        
	
	}
	
	//to show customer details of his own
	public void viewcustomerdetails(Connection conn, Logger log) 
	{

		
		String aq1 = "select customer_id ,customer_username  ,customer_password ,customer_name ,customer_phno from customer_login_details where customer_username='"+getFineusername()+"'";
		Statement sta1 = null;
		ResultSet ars1;
		log.info("customer_id \t customer_username \tcustomer_password \tcustomer_name \tcustomer_phno");
		
		 // to initialize Statement connection
		try {
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				String str = ars1.getInt(1)+"\t\t\t"+ars1.getString(2)+"\t\t  "+ars1.getString(3)+" \t\t"+ars1.getString(4)+"\t "+ars1.getString(5);
				log.info(str);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			closeconection(sta1);
			
		}
	
	     try {
	    	//calling customer home or logout method
			customerreturnhomelogout(conn,log);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        
	}
	
	//to change customer log password
	public void changepassword(Connection conn, Logger log) 
	{
		
		 String newpaswd = regobj.admpassword();
		 String qup = "update customer_login_details set customer_password=? where customer_username='"+getFineusername()+"'";
		 PreparedStatement pc2 = null;
		
		 // to initialize Statement connection
		 try {
			pc2 = conn.prepareStatement(qup);
			 pc2.setString(1, newpaswd);
		
			
			 pc2.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			closeconection(pc2);
		}
		
		 log.info("you password is updated succesfully....");
		 try {
			//calling customer home or logout method
			customerreturnhomelogout(conn,log);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		 
		 
			
	}
	
	// to show all orders of particular customer
	public void viewallyourorders(Connection conn, Logger log) 
	{
	
		   	String qvyo = "select customer_username , customer_phno, product_name,ordered_date, prodect_count from customer_order_details where customer_username='"+getFineusername()+"'";
	        log.info("customer_username \tcustomer_phno \tproduct_name \tordered_date \tprodect_count ");
	        Statement vyo = null;
	        ResultSet rvyo;
			
	        // to initialize Statement connection
	        try {
				vyo = conn.createStatement();
				 rvyo= vyo.executeQuery(qvyo); 
				  while(rvyo.next())
			        {
					  String str = rvyo.getString(1)+"\t"+rvyo.getLong(2)+"\t\t"+rvyo.getString(3)+"\t\t"+rvyo.getDate(4)+"\t\t"+rvyo.getInt(5);
			        	log.info(str);
			        }
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			
			//closing statement connection
			finally
			{
				closeconection(vyo);
				
			}
			
	        try {
	        	//calling customer home or logout method
				customerreturnhomelogout(conn,log);
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
	        
	}

	//to ask customer to return to home or logout
	public void customerreturnhomelogout(Connection conn, Logger log) throws SQLException
	{
		Mainclass obj= new Mainclass();
		log.info(" enter <h> to return home OR enter <lg> to logout : ");
		String output= sccu.next();
		
		//validating customer input
		if(output.equals("h"))
		{
			//calling customer home
			obj.customerhome(conn);		
		}
		else
		{
			log.info("you have been logged out !!!!");
			conn.close();
			
		}
	}

	public static String getFineusername() {
		return fineusername;
	}
	
	public void closeconection(Statement stmt)
	{
		if(stmt!=null) 
		{ 
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeconection(PreparedStatement stmt)
	{
		if(stmt!=null) 
		{ 
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
