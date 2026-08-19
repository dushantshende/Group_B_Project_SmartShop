package com.ds.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ds.DbConnection.DbConnection;
import com.ds.Repo.IPurchases;
import com.ds.model.Purchases;

public class PurchaseService implements IPurchases {

	private String query="""
			INSERT INTO PURCHASES(QUANTITY) VALUES(?);
			""";
	
	private String checkStock="""
			SELECT QUANTITY FROM PRODUCTS 
			WHERE PRODUCT_ID=?;
			""";
	
	private String updateStock="""
			UPDATE PRODUCTS
			SET QUANTITY= QUANTITY-?
			WHERE PRODUCT_ID=?;
			""";
	
	private String purchaseHistoryQuery ="{ call get_purchase_history()}";
	
	private String viewCartQuery="{call get_user_cart(?)}";
	
	Purchases purchase;
	DbConnection dbConnection;
	
	public PurchaseService(Purchases purchase, DbConnection dbConnection) {
		this.purchase=purchase;
		this.dbConnection=dbConnection;
	}
	
	@Override
	public void setPurchaseOrder(Integer userId) {
		System.out.println("----------------Purchase Order Console-------------");
		Scanner scan=new Scanner(System.in);
		
		System.out.println("Enter Product Id: ");
		Integer productId=scan.nextInt();
		
		
		System.out.println("Enter Quantity : ");
		Integer quantity=scan.nextInt();
		
		purchase.setQuantity(quantity);
		
		checkProductStock(userId, productId);
		
		
		
		
	}
	
	
	
	
	
	public void savePurchase(Integer userId, Integer productId) {
		Connection con=dbConnection.getDbConnection();
		
		String query="""
					INSERT INTO PURCHASES
					(USER_ID, PRODUCT_ID, PURCHASE_DATE, QUANTITY)
					VALUES(?,?,?,?);
					""";
				
		try {
			PreparedStatement psmt=con.prepareStatement(query);
			psmt.setInt(1, userId);
			psmt.setInt(2, productId);
			psmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			psmt.setInt(4, purchase.getQuantity());
			
			psmt.executeUpdate();
			updateProductStock(productId);
			System.out.println("purchase order placed successfully");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void checkProductStock(Integer userId, Integer productId) {
		Connection con=dbConnection.getDbConnection();
		
		try {
			PreparedStatement psmt=con.prepareStatement(checkStock);
			psmt.setInt(1, productId);
			
			ResultSet res=psmt.executeQuery();
			if(res.next()) {
				Integer availableQuantity=res.getInt("quantity");
				System.out.println("Available Quantity: "+availableQuantity);
				
				if(purchase.getQuantity() <=availableQuantity) {
					savePurchase(userId,productId);
				}else {
					System.out.println("Insufficient Stock");
				}
			}else {
				System.out.println("Product not Found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateProductStock(Integer productId) {
		Connection con=dbConnection.getDbConnection();
		try {
			PreparedStatement psmt=con.prepareStatement(updateStock);
			psmt.setInt(1, purchase.getQuantity());
			psmt.setInt(2, productId);
			
			int result=psmt.executeUpdate();
			if(result > 0) {
				System.out.println("Product stock updated successfully");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void purchaseHistory() {
		Connection con=dbConnection.getDbConnection();
		try {
			CallableStatement cs=con.prepareCall(purchaseHistoryQuery);
			
			ResultSet rs=cs.executeQuery();
			System.out.println("------------------Purchase History--------------------");
			
			while(rs.next()) {
				System.out.println("Purchase Id : "+rs.getInt("purchase_id"));
				System.out.println("User Id     : "+rs.getInt("user_id"));
				System.out.println("Username    : "+rs.getString("username"));
				System.out.println("Product Id  : "+rs.getInt("product_id"));
				System.out.println("Product Name: "+rs.getString("product_name"));
				System.out.println("Purchase Date: "+rs.getDate("purchase_date"));
				System.out.println("Quantity    : "+rs.getInt("quantity"));
				
				System.out.println("-------------------------------------------------------");
			}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public void viewCart(Integer userId) {
		Connection con = dbConnection.getDbConnection();
		try {
			CallableStatement cs=con.prepareCall(viewCartQuery);
			cs.setInt(1, userId);
			
			ResultSet rs=cs.executeQuery();
			
			double totalAmount =0;
			
			System.out.println("-----------------View Cart-------------------");
			System.out.println("Product Name | Quantity | Price | Subtotal");
			System.out.println("----------------------------------------------");
			
			while(rs.next()) {
				String productName=rs.getString("product_name");
				int quantity =rs.getInt("quantity");
				double price=rs.getDouble("price");
				double subtotal=rs.getDouble("subtotal");
				
				System.out.println(	productName + " | "+ quantity + " | "+ price+" | "+	subtotal);
				totalAmount=totalAmount+subtotal;
			}
			System.out.println("--------------------------------------------------------");
			System.out.println("Total Amount: "+totalAmount);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
