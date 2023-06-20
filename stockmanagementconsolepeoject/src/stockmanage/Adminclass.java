package stockmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

public class Adminclass {


	List<Integer> prodids = new ArrayList<>();
	
	Scanner scad = new Scanner(System.in);
	
	Customerclasss custoobj = new Customerclasss();
	
	RegExmethods regobj = new RegExmethods();
	
	//to show product details to the admin
	public void seeproducts(Connection conn, Logger log) 
	{
		String aq1 = "select product_id, product_name , product_price , product_avalible , producta_created_date from product_details";
		Statement sta1 = null  ;
		ResultSet ars1;
		log.info("product_id \t product_name \t product_price \t product_avalible \t producta_created_date");
		Mainclass obja2= new Mainclass();
		try {
			
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				String str = ars1.getInt(1)+"\t\t"+ars1.getString(2)+"\t\t\t"+ars1.getInt(3)+"\t \t"+ars1.getInt(4)+"\t \t"+ars1.getDate(5);
				log.info(str);
			}
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		}
	 

		//closing statement connection
		finally
		{
			custoobj.closeconection(sta1);
			
		}	
		
		//calling home method for admin 
		obja2.adminreturnhomelogout(conn,log);
	}
		//to get all product ids of products in store
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
				custoobj.closeconection(sta1);
				
			}
			
	}
	
		//to add products in store
	public void addproducts(Connection conn, Logger log) 
	{
		
		String qadd = "insert into product_details values (?,?,?,?,now())";
	
		int pid;
		int pavail;
		int pprice;
		String pname;
		boolean flg = true;
		while(flg)
		{
			//calling methods for get product ids
			getallproductsids(conn);
			
			pid = regobj.idee();
			
			if(!prodids.contains(pid))
			{
					
					pname = regobj.productname();
				    pprice= regobj.prodprice();
					pavail = regobj.prodcount();
			
					PreparedStatement pc = null;
					
					//executing admin query					
					try {
						
						pc = conn.prepareStatement(qadd);
						pc.setInt(1, pid);
						pc.setString(2, pname);
						pc.setInt(3,pprice );
						pc.setInt(4, pavail);
					
						
						 pc.executeUpdate();
						 
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
					//closing statement connection
					finally
					{
						custoobj.closeconection(pc);
						
					}
					flg=false;
			}
			else
			{
				log.info("oops product id already exists in store");
			}
	   }
		
			log.info("your have succusfully added product details ");
			Mainclass obja2= new Mainclass();
			
			//calling home method for admin 
				obja2.adminreturnhomelogout(conn,log);
		
			
	}

	//delete products from store
	public void deleteprodusts(Connection conn, Logger log) 
	{
		Mainclass clsobj= new Mainclass();
		String qdel = "delete from product_details where product_id=?";
		int pid;
		
		boolean flg = true;
		while(flg)
		{
			//calling method for getting product ids
			getallproductsids(conn);
			
			pid = regobj.idee();
			if(prodids.contains(pid))
			{
				
				PreparedStatement pc = null;
				
				//executing admin query	
				try {
					pc = conn.prepareStatement(qdel);
					pc.setInt(1, pid);
					
					pc.executeUpdate();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				//closing statement connection
				finally
				{
					custoobj.closeconection(pc);
					
				}
				flg=false;
			}
			else
			{
				log.info("oops product id already exists in store");
			}
		}
	
		log.info(" product has been deleted succesfully ....");
		
		//calling home method for admin 
			clsobj.adminreturnhomelogout(conn,log);
		
	}
	
	public void insettoadminorders(Connection conn,Logger log)
	{
		String q = "insert into admin_ordered_details values (?,?,?,now())";
		int pid;
		int pcount;
		
		boolean flg2 = true;
		//iterating ti check whether product id contains in list
		while(flg2)
		{
			String psellername;
			
			pid = regobj.idee();
			getallproductsids(conn);
			if(prodids.contains(pid))
			{		
					
					psellername = regobj.sellername();		
					pcount = regobj.prodcount();
					PreparedStatement pc=null;
					//executing admin query	
					try {
						pc = conn.prepareStatement(q);
						pc.setInt(1, pid);			
						pc.setString(2, psellername);			
						pc.setInt(3, pcount);	
						 pc.executeUpdate();
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					}						
					//closing statement connection
					finally
					{
						custoobj.closeconection(pc);
					}					
					//updating product count in store after admin place order for store 
					updateprodavailble(conn,pcount,pid);
					flg2= false;
			}
			else
			{
				log.info("oops product id   does not exists in store");
			}
		}
	}

	//to order products for store by admin
	public void adminorderedproducts(Connection conn, Logger log) 
	{
		Mainclass clsobj= new Mainclass();
		boolean flg =  true;
		//iterating for insert inserting multiple records
		while(flg)
		{
			insettoadminorders(conn,log);								
			//condition to add more products
			 log.info("If you want to order more products then enter <1> , else enter <0> : ");
				int  ad=scad.nextInt();
				if(ad==0)
				{
					flg=false;
					
				}	
		}
		//calling home method for admin 
			log.info("your order is placed succesfully !!!!! ");
				clsobj.adminreturnhomelogout(conn,log);	
	}
	
	//updating product count in store after admin place order for store 
	public void updateprodavailble(Connection conn ,int pcount , int pid)
	{
		 String qq = "select product_avalible from product_details where product_id='"+pid+"'";
		 Statement pcs = null ;
		 int aval=0 ;
		 
		//executing admin query	
		try {
			pcs = conn.createStatement();
			 ResultSet r = pcs.executeQuery(qq);
			
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
			custoobj.closeconection(pcs);
			
		}
		
		String qup = "update product_details set product_avalible=?+? where product_id=?";
		 PreparedStatement pc2=null;
		 
		//executing admin query	
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
			custoobj.closeconection(pc2);
			
		}
	}
	
	//to show admin log details for admin
	public void viewadmindetails(Connection conn, Logger log) 
	{
		
		String aq1 = "select ad_username  , ad_password, ad_name,ad_email from admin_log_details where ad_username='"+Mainclass.getAdmusernmae()+"'";
		Statement sta1 = null;
		ResultSet ars1;
		log.info("ad_username \t\t ad_password \t\t ad_name \t\t adm_email");
		
		//executing admin query	
		try {
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				String str = ars1.getString(1)+"\t\t"+ars1.getString(2)+"\t\t"+ars1.getString(3)+"\t\t"+ars1.getString(4);
				log.info(str);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			custoobj.closeconection(sta1);
			
		}
	
		//calling home method for admin 
		Mainclass obja1= new Mainclass();
		obja1.adminreturnhomelogout(conn,log);
		
		
	}
	
	public void changeadminpassword(Connection conn, Logger log) 
	{
		 
		String aq1 = "select ad_email from admin_log_details where ad_username='"+Mainclass.getAdmusernmae()+"'";
		Statement sta1 = null;
		ResultSet ars1;
		String admmail = "";
		
		//executing admin query	
		try {
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				admmail = ars1.getString(1);				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			custoobj.closeconection(sta1);
			
		}
		
		
		 String newpaswd = regobj.admpassword();
		 String qup = "update admin_log_details set ad_password=? where ad_username='"+Mainclass.getAdmusernmae()+"'";
		 PreparedStatement pc2=null;
		 
		//executing admin query	
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
			custoobj.closeconection(pc2);
			
		}
		
		 log.info("you password is updated succesfully....");
		 
			//calling home method for admin 
		 Mainclass objc1= new Mainclass(); 
			objc1.adminreturnhomelogout(conn,log);
	}
		 
    //to view all customer orders by admin
	public void viewallcustomerorders(Connection conn, Logger log) 
	{

		String qvyo = "select customer_username , customer_phno, product_name,ordered_date, prodect_count from customer_order_details ";
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
				 custoobj.closeconection(vyo);
					
				}
 
		//calling home method for admin 
	        Mainclass objc1= new Mainclass();
			objc1.adminreturnhomelogout(conn,log);
		
	}
	
}
