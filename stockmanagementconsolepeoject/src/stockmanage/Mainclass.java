package stockmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.*;

public class Mainclass {

	public final static  Logger log= LogManager.getLogger(Mainclass.class.getName());
	public static String admusernmae = "";
	
	Customerclasss custoobj = new Customerclasss();
	
	RegExmethods regobj = new RegExmethods();
	

	public static String getAdmusernmae() {
		return admusernmae;
	}



	public static void setAdmusernmae(String admusernmae) {
		Mainclass.admusernmae = admusernmae;
	}

	Scanner sc = new Scanner(System.in);
	
	//to check whether person is admin or customer or new customer
	public void whoistheperson(Connection conn ,Logger log2) throws SQLException
	{
		Customerclasss objc = new Customerclasss();
		 Mainclass obj= new Mainclass(); 
		 boolean flag= true;
		 while(flag)
		 {
			 log.info(" ENTER '1' : if you are admin" );
			log.info( "ENTER '2' : if you are customer");
			log.info(" ENTER '3' : if you are new customer ");
			log.info(" enter your response here :  ");
			
			//validate user input send response to user
			 int who = sc.nextInt();
			 if(who==1)
			 {
				 obj.adminverification(conn,log2);
				 flag= false;
			 }
			 else if( who==2)
			 {
				 objc.userverification(conn,log2);
				 flag= false;
			 }
			 else if (who==3)
			 {
				 obj.userregistration(conn,log2);
				 flag= false;
			 }
			 else
			 {
				 log2.info(" you have enter incorrect option  ....");
				 log2.info("enter any one of above mentioned option ....");
				 flag= true;
			 }
		 }
	}
	
	
	//to get admin password of entered log user
	public String getpassword(Connection conn,String pswd)
	{
		
		 String q11 = "select ad_password from admin_log_details where ad_username='"+pswd+"'";
		 Statement stmt11 = null;
		 ResultSet rs11;
		String pasword ="";
		
		//initiating statement connection
		try {
			stmt11 = conn.createStatement();
			 rs11 = stmt11.executeQuery(q11);
			 while(rs11.next())
			 {
				 pasword = rs11.getString(1);
				
				 
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			custoobj.closeconection(stmt11);
		}
		return pasword;
	}
	
	//verifying admin log details with database
	public void adminverification(Connection conn, Logger log) 
	{
		boolean aflag= true;
		while(aflag) {
			
			 Mainclass cobj= new Mainclass();
			
			 String name= regobj.username();
			 
			 // assigning login user to static variable
			 setAdmusernmae(name);
			
			 String paswd= regobj.userpassword();
			
			//calling method to get password from database according user entered log details
			 String verifypas= getpassword(conn , name);
			 
			//verifying  admin log details with the database
			 if(verifypas.equals(paswd))
		     {
				 log.info("--------------------------------------------------");
		         String str = "|                  welcome "+name+"                  |";
		         log.info("--------------------------------------------------");
		         log.info(str);
				 log.info("admin username and password is verified");
				 	log.info("you haba been succesfull logged in");
				 	try {
				 		
				 		//forwarding to admin home
						cobj.adminhome(conn,log);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				 	aflag= false;
			
			
		 }
		 else
		 {
			 log.info("enterd login details  are not correct");
			 aflag=true;
		 }
		} 
	}

	List<String> custusernames = new ArrayList<>();
	
	public void getallcustuseranames(Connection conn  )
	{
		String aq1 = "select customer_username  from customer_login_details";
		Statement sta1 = null;
		ResultSet ars1;		
		 // to initialize Statement connection
		try {
			sta1 = conn.createStatement();
			ars1= sta1.executeQuery(aq1);
			while(ars1.next())
			{
				custusernames.add(ars1.getString(1));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//closing statement connection
		finally
		{
			custoobj.closeconection(sta1);
			
		}
	}
	
	//to register new customer
	public void userregistration(Connection conn, Logger log2) 
	{
		
		String q = "insert into customer_login_details(customer_name, customer_username, customer_password, customer_phno) values (?,?,?,?)";
		
		

		String cname;
		String cusername;
		 String cpaswd;
		long cphno;
		boolean flg =true;
		while(flg)
		{
			
			cname =regobj.custname();
			cusername = regobj.username();
			
			if(!custusernames.contains(cusername))
			{
				flg=false;
				cpaswd = regobj.userpassword();
				cphno = regobj.custphone();
				PreparedStatement pc = null;
				
				//initiating statement connection
				try {
					
					pc = conn.prepareStatement(q);
					pc.setString(1, cname);
					pc.setString(2, cusername);
					pc.setString(3, cpaswd);
					pc.setLong(4, cphno);
					
					 pc.executeUpdate();
				
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				//closing statement connection
				finally
				{
					custoobj.closeconection(pc);
					
				}
			}
			else
			{
				log.info(" !!! username already exists try with another useranme...");
			}
		}
			
			
			
			
			log.info("your have succusfully created account ");
			log.info(" enter your details to login ");
			
			//calling to login customer method to logging after crating account
			 Customerclasss cuobj12 = new Customerclasss();			
				cuobj12.userverification(conn,log2);
			
	
	}
	

	//to provide  admin to chose want to do after logging
	public void adminhome(Connection conn, Logger log2) throws SQLException 
	{
		
		 Adminclass adobj = new Adminclass();
		log.info(" ENTER [1]: if you want to change the admin password   ");
		log.info(" ENTER [2]:if you want to see the productd in the store  ");
		log.info(" ENTER [3]:if you want to add  more productss into the  ");
		log.info(" ENTER [4]:if you want to delete products from the store   ");
		log.info(" ENTER [5]:if you want to order the products for the store   ");
		log.info(" ENTER [6]:if you want to see customer order details  ");
		log.info(" ENTER [7]:if you want to see your profile details   ");
		 log.info("-----------------------------------------------------------------------------");
		log.info("  enter the numer for you case : ");
		int input =sc.nextInt();
		
		//routing admin request according admin input
		switch (input) {
		  case 1:
			  adobj.changeadminpassword(conn,log2);
		    break;
		  case 2:
			  adobj.seeproducts(conn,log2);
			  
		    break;
		  case 3:
			  adobj.addproducts(conn,log2);
		    break;
		  case 4:
			  adobj.deleteprodusts(conn,log2);
		    break;
		  case 5:
			  adobj.adminorderedproducts(conn,log2);
		    break;
		  case 6:
			  adobj.viewallcustomerorders(conn,log2);
		    break;
		  case 7:
			  adobj.viewadmindetails(conn,log2);
		    break;
		  default: 
			  adminreturnhomelogout(conn,log2);
			 break;

		    
		}
			
	
	}
	
	//to provide admin to chose want to do after logging
	public void customerhome(Connection conn) throws SQLException
	{
		
		 Customerclasss cuobj44 = new Customerclasss();
		log.info("ENTER [1]: if you want to change the password    ");
		log.info("ENTER [2]: if you want to see the products and their details in the store   ");
		log.info("ENTER [3]: if you want to buy products in  the store enter  ");
		log.info("ENTER [4]: if you want to see the products that you have ordered ");
		log.info("ENTER [5]: if you want to see the profile   ");
		 log.info("----------------------------------------------------------------------------------------------------------");
		log.info("enter the number for your case : ");
		int input =sc.nextInt();
		
		//routing customer request according customer input
		switch (input) {
		  case 1:
			  cuobj44.changepassword(conn,log);
		    break;
		  case 2:
			  cuobj44.seeproductss(conn,log);	  
		    break;
		  case 3:
			  cuobj44.customerorders(conn,log);
		    break;
		  case 4:
			  cuobj44.viewallyourorders(conn,log);
		    break;
		  case 5:
			  cuobj44.viewcustomerdetails(conn,log);
		    break;
		   default:
			   cuobj44.customerreturnhomelogout(conn,log);
			   break;
		
		}
	
	}
	
	//asking to admin whether he want to return home or logout
	public void adminreturnhomelogout(Connection conn,Logger log) 
	{
		Mainclass obj= new Mainclass();
		String str =  "----------------------------------------------------------------------------------------------------------";
		log.info(str);
		log.info(" enter <h> to return home OR enter <lg> to logout : ");
		String output= sc.nextLine();
		
		//validating admin input
		if(output.equals("h"))
		{
			 log.info(str);
			
			try {
				obj.adminhome(conn,log);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		else
		{
			log.info("you have been logged out !!!!");
			 log.info(str);

				//closing database connection
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//main method
	public static void main(String[] args) throws SQLException {
		
		
		String scj= "jdbc:mysql://localhost/stock_manage";
		Connection conn = DriverManager.getConnection(scj,"root","SAI@abc123");
		
		 Mainclass mainobj= new Mainclass();
		 //calling this method to know who is the person
		 mainobj.whoistheperson(conn ,log);
		 	
		
	}

}
