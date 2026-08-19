package com.ds.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ds.service.PurchaseService;
import com.ds.service.UserService;

public class SmartShopTest {

	public static void main(String[] args) {
		
			ApplicationContext ap=new ClassPathXmlApplicationContext("applicationContext.xml");

			/*UserService us=ap.getBean("userservice",UserService.class);
			us.registerUser();
			*/
		
			/*ProductService prod=ap.getBean("productservice",ProductService.class);
			prod.insertProduct();
			*/
			/*
			PurchaseService purchase=ap.getBean("purchaseService",PurchaseService.class);
			purchase.setPurchaseOrder();*/
			
			UserService us=ap.getBean("userservice", UserService.class);
			us.loginUser();
	}
}
