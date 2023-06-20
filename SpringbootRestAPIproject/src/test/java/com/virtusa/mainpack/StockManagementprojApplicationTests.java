package com.virtusa.mainpack;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.virtusa.mainpack.entity.Productdetails;

@SpringBootTest
class StockManagementprojApplicationTests {

	@Test
	void selectproduct()
	{
		RestTemplate template = new RestTemplate();
		Productdetails prod = template.getForObject("http://localhost:8088/admin/prouctsdetails/1001", Productdetails.class);
		System.out.println("productname"+prod.getProductname());
		assertNotNull(prod);
		
	}

}
