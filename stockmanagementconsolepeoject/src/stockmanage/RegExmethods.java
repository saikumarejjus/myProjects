package stockmanage;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegExmethods {

	public final static  Logger logg= LogManager.getLogger(Mainclass.class.getName());
	public static String str = "[0-9]+";
	//to take get product id from user input
	public int idee()
	{
		logg.info("Enter the id of the product, which should have only numbers ");
		Scanner sc1 = new Scanner(System.in);
		String iid= sc1.nextLine();
		
		//to re-enter priduct id if user enter's wrong product id
		while(true)	
		{	
			//validating user input with variable pattern
			if(Pattern.matches(str,iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid product id ");
				logg.info("Enter the id of the product, which should have only numbers ");
				iid= sc1.nextLine();
			}			
		}
		int pid= Integer.parseInt(iid);
		return pid;	
	}
	
	//to take get phone number from user input
	public int custphone()
	{
		logg.info("Enter  phone number, which should have only numbers ");
		Scanner sc1 = new Scanner(System.in);
		String iid= sc1.nextLine();
		//to re-enter phone number if user enter's wrong pattern
		while(true)	
		{		
			//validating user input with variable pattern
			if(Pattern.matches("[6789][0-9]{9}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid phone number ");
				logg.info("Enter  phone number, which should have only numbers ");
				iid= sc1.nextLine();
			}			
		}
		int pid= Integer.parseInt(iid);
		return pid;	
	}
	//to take get price of the product from user input
	public int prodprice()
	{
		//validating user input with variable pattern
		logg.info("Enter the price of the product, which should have only numbers ");
		Scanner sc1 = new Scanner(System.in);		
		String iid= sc1.nextLine();
		//to re-enter priduct price if user enter's wrong patern
		while(true)	
		{
			if(Pattern.matches(str,iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid product price ");
				logg.info("Enter the price of the product, which should have only numbers ");
				iid= sc1.nextLine();
			}			
		}
		int pid= Integer.parseInt(iid);		
		return pid;	
	}
	//to take get qantity of the product from user input
	public int prodcount()
	{
		logg.info("Enter the qantity of the product, which should have only numbers ");
		Scanner sc1 = new Scanner(System.in);		
		String iid= sc1.nextLine();	
		//to re-enter priduct quantity if user enter's wrong input
		while(true)	
		{	
			//validating user input with variable pattern
			if(Pattern.matches(str,iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid product count ");
				logg.info("Enter the quantity of the product, which should have only numbers ");
				iid= sc1.nextLine();
			}		
		}
		int pid= Integer.parseInt(iid);
		return pid;	
	}
	//to take get name of the product from user input
	public String productname()
	{
		logg.info("Enter the name of the product, which should have only letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter name of the product if user enter's wrong pattern 
		while(true)	
		{	
			//validating user input with variable pattern
     		if(Pattern.matches("[a-zA-Z ]{3,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid product name ");
				logg.info("Enter the name of the product, which should have only letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	//to take get name of the seller from user input
	public String sellername()
	{
		logg.info("Enter the name of the seller, which should have only letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter name of the seller  if user enter's wrong pattern
		while(true)	
		{	
			//validating user input with variable pattern
     		if(Pattern.matches("[a-zA-Z ]{3,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid product name ");
				logg.info("Enter the name of the seller, which should have only letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	//to take get new password from user input
	public String admpassword()
	{
		logg.info("Enter new password, which should have number, letters, special characters letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter userlogin password if user enter's wronguserlogin password pattern
		while(true)	
		{	
			//validating user input with variable pattern
     		if(Pattern.matches("[A-Za-z0-9@!#$%&*]{8,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid password ");
				logg.info("Enter new password, which should have number, letters, special characters letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	
	//to take get password from user input
	public String userpassword()
	{
		logg.info("Enter  password, which should have number, letters, special characters letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter userlogin password if user enter's wrong userlogin password pattern
		while(true)	
		{		
			//validating user input with variable pattern
     		if(Pattern.matches("[A-Za-z0-9@!#$%&*]{8,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid password ");
				logg.info("Enter  password, which should have number, letters, special characters letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	
	//to take get username from user input
	public String username()
	{
		logg.info("Enter  username(size=min4), which should have number, letters letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter username if user enter's wrong username
		while(true)	
		{	
			//validating user input with variable pattern
     		if(Pattern.matches("[A-Za-z0-9]{4,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter valid username ");
				logg.info("Enter  username(size=min4), which should have number, letters letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	//to take get full name from user input
	public String custname()
	{
		logg.info("Enter  full name, which should have  letters letters ");
		Scanner sc1 = new Scanner(System.in);				
		String iid= sc1.nextLine();	
		//to re-enter ful name if user enter's wrong pattern
		while(true)	
		{		
			//validating user input with variable pattern
     		if(Pattern.matches("[A-Za-z ]{3,}",iid))
			{
				break;				
			}
			else
			{
				logg.info("Enter full name ");
				logg.info("Enter  full name, which should have  letters letters ");
			     iid= sc1.nextLine();
			}			
		}
		return iid;	
	}
	
}
