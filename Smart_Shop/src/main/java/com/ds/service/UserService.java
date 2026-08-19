package com.ds.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ds.DbConnection.DbConnection;
import com.ds.Repo.IUserDetails;
import com.ds.model.User;

public class UserService implements IUserDetails{

	String register="""

			INSERT INTO USERS(FIRST_NAME,LAST_NAME,USERNAME, PASSWORD, CITY,EMAIL,MOBILE)VALUES(?,?,?,?,?,?,?);

			""";
	
	String login="""
			SELECT USER_ID, USERNAME, PASSWORD , ROLE
			FROM USERS 
			WHERE USERNAME=? AND PASSWORD=? ;
			""";
	
	
	
	//HAS-A relationship
	private DbConnection dbConnection;
	private User user;
	private ProductService productService;
	private PurchaseService purchaseService;

	//instance variable
	private Integer loggedInUserId;

	//constructor injection 
	public UserService (User user,DbConnection dbConnection, ProductService productService, PurchaseService purchaseService) {
		this.user=user;
		this.dbConnection=dbConnection;
		this.productService=productService;
		this.purchaseService=purchaseService;
	}

	@Override
	public void registerUser() {
		System.out.println("User Registration Page");
		Scanner scan=new Scanner(System.in);

		System.out.println("Enter First Name : ");
		String firstname=scan.next();
		user.setFirst_name(firstname);

		System.out.println("Enter Last Name: ");
		String lastname=scan.next();
		user.setLast_name(lastname);


		System.out.println("Enter username: ");
		String username=scan.next();
		user.setUsername(username);


		System.out.println("Enter password: ");
		String password=scan.next();
		user.setPassword(password);

		System.out.println("Enter city: ");
		String city=scan.next();
		user.setCity(city);
		
		System.out.println("Enter Email: ");
		String email=scan.next();
		user.setEmail(email);
		
		System.out.println("Enter Mobile: ");
		Long mobile=scan.nextLong();
		user.setMobile(mobile);

		//Here user Insert into DatabaseTable
		saveUser();

	}

		
		@Override
		public void saveUser() {
			
			Connection con=dbConnection.getDbConnection();
			try {
				PreparedStatement ps=con.prepareStatement(register);
				ps.setString(1,user.getFirst_name());
				ps.setString(2, user.getLast_name());
				ps.setString(3,	user.getUsername());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getCity());
				ps.setString(6, user.getEmail());
				ps.setDouble(7, user.getMobile());
				
				ps.executeUpdate();
				
				System.out.println("1 recored insert successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}

		@Override
		public void loginUser() {
			Scanner scan=new Scanner(System.in);
			System.out.println("----------------------Login------------------------");
			
			System.out.println("Enter username: ");
			String username=scan.next();
			user.setUsername(username);
			
			System.out.println("Enter password: ");
			String password=scan.next();
			user.setPassword(password);
			checkUser();
			
		}
		
		public void checkUser() {
			Connection con=dbConnection.getDbConnection();
			
			try {
				PreparedStatement ps=con.prepareStatement(login);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					loggedInUserId= rs.getInt("user_id");
					String role= rs.getString("role");
					
					System.out.println("Login Successful !");
					System.out.println("user id: "+loggedInUserId);
					System.out.println("role: "+role);
					
					if (role.equals("admin")) {
						System.out.println("Welcome Admin");
						adminMenu();
					}else {
						System.out.println("Welcome User");
						userMenu();
					}
						
				}else {
					System.out.println("Invalid Username or password ");
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

	}
		
		public void adminMenu() {
			Scanner scan=new Scanner(System.in);
			
			while(true) {
			System.out.println("-------------Admin Menu---------------");
			System.out.println("1. Add Product");
			System.out.println("2. Update Product");
			System.out.println("3. Delete Product");
			System.out.println("4. View Products");
			System.out.println("5. Purchase History");
			System.out.println("6. Logout");
			
			
			System.out.println("Enter Your Choice: ");
			int choice=scan.nextInt();
			
			switch(choice) {
			
			case 1:
				System.out.println("Add Product selected");
				productService.insertProduct();
				break;
			
			case 2:
				System.out.println("Update Product selected");
				productService.updateProduct();
				break;
				
			case 3:
				System.out.println("Delete Product selected");
				productService.deleteProduct();
				break;
				
			case 4:
				System.out.println("View Product selected");
				productService.viewProduct();
				break;
				
			case 5:
				System.out.println("Purchase History selected");
				purchaseService.purchaseHistory();
				break;
				
			case 6:
				System.out.println("Logout Successfully");
				return;
				
				default :
					System.out.println("Invalid choice");
			}
			
		}
		}
		public void userMenu() {
			
			Scanner scan=new Scanner(System.in);
			while(true) {
			System.out.println("-------------User Menu-----------------");
			System.out.println("1. View Products");
			System.out.println("2. Search Product");
			System.out.println("3. View product Details");
			System.out.println("4. Purchase Product");
			System.out.println("5. View Cart");
			System.out.println("6. View All Past Orders");
			System.out.println("7. Logout");
			
			System.out.println("Enter Your Choice: ");
			int choice=scan.nextInt();
			
			switch(choice) {
			
			case 1: 
				System.out.println("view products selected");
				productService.viewProduct();
				break;
			
			case 2:
				System.out.println("Search Product selected");
				productService.searchProduct();
				break;
				
			case 4:
				System.out.println("Purchase Product selected");
				purchaseService.setPurchaseOrder(loggedInUserId);
				break;
				
			case 5: 
				System.out.println("View cart selected");
				purchaseService.viewCart(loggedInUserId);
				break;
			case 7:
				System.out.println("Logout Successfully");
				return;
				
				default:
					System.out.println("Invalid choice");
			}
		}}

}

