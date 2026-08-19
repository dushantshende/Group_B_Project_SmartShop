package com.ds.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ds.DbConnection.DbConnection;
import com.ds.Repo.IProducts;
import com.ds.model.Products;

public class ProductService implements IProducts {
	//ProductService ps=new ProductService();
	final String insert="""
			INSERT INTO PRODUCTS (PRODUCT_NAME,DISCRIPTION,PRICE,QUANTITY)VALUES(?,?,?,?);
			""";
	
	final String view="""
			SELECT * FROM PRODUCTS;
			""";
	
	final String search="""
			SELECT * FROM PRODUCTS 
			WHERE LOWER(PRODUCT_NAME) LIKE LOWER(?)
			OR LOWER(DISCRIPTION) LIKE LOWER(?);
			""";
	
	final String delete="""
			DELETE FROM PRODUCTS WHERE PRODUCT_ID=?;
			""";
	
	final String update="""
			UPDATE PRODUCTS 
			SET PRODUCT_NAME=?, DISCRIPTION =?, PRICE=?,QUANTITY=?
			WHERE PRODUCT_ID=?;
			""";
	
	
	
	//HAS - A property
	private Products product;
	private DbConnection dbConnection;
	
	
	public ProductService(Products product, DbConnection dbConnection){
		this.product=product;
		this.dbConnection=dbConnection;
	}
	public void insertProduct() {
		System.out.println("-----------------Product Console-------------");
		Scanner scan=new Scanner(System.in);
		
		System.out.println("Enter Product Name: ");
		String product=scan.nextLine();
		this.product.setProduct_name(product);
		
		//scan.nextLine();
		
		System.out.println("Enter product Description: ");
		String description =scan.nextLine();
		this.product.setDescription(description);
		
		System.out.println("Enter Product Price: ");
		Double price=scan.nextDouble();
		this.product.setPrice(price);
		
		System.out.println("Enter Product Quantity: ");
		Integer quantity=scan.nextInt();
		this.product.setQuantity(quantity);
		
		addProducts();
		
	}
	
	
	@Override
	public void addProducts() {
		
		Connection con=dbConnection.getDbConnection();
		try {
			PreparedStatement psmt=con.prepareStatement(insert);
			
			psmt.setString(1, product.getProduct_name());
			psmt.setString(2, product.getDescription());
			psmt.setDouble(3, product.getPrice());
			psmt.setInt(4, product.getQuantity());
			psmt.executeUpdate();
			System.out.println("1 record insert successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteProduct() {
		Scanner scan=new Scanner(System.in);
		System.out.println("------------Delete Product-----------------------");
		
		System.out.println("Enter Product Id: ");
		Integer productId=scan.nextInt();
		product.setProduct_id(productId);
		
		Connection con=dbConnection.getDbConnection();
					try {
						PreparedStatement psmt=con.prepareStatement(delete);
						psmt.setInt(1, product.getProduct_id());
						
						int result=psmt.executeUpdate();
						if (result > 0) {
							System.out.println("Product deleted Successfully");
						}else {
							System.out.println("Product not found");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
	}

	@Override
	public void updateProduct() {
		
		Scanner scan=new Scanner(System.in);
		System.out.println("-----------------Update Product------------------");
		System.out.println("Enter Product Id : ");
		Integer productId=scan.nextInt();
		product.setProduct_id(productId);
		
		scan.nextLine();
		
		System.out.println("Enter Product Name: ");
		String productName= scan.nextLine();
		
		System.out.println("Enter product Description: ");
		String description=scan.nextLine();
		
		System.out.println("Enter Product price: ");
		Double price=scan.nextDouble();
		
		System.out.println("Enter Product Quantity: ");
		Integer quantity=scan.nextInt();
		
		
		
		Connection con=dbConnection.getDbConnection();
		try {
			PreparedStatement psmt=con.prepareStatement(update);
			psmt.setString(1, productName);
			psmt.setString(2, description);
			psmt.setDouble(3, price);
			psmt.setInt(4, quantity);
			psmt.setInt(5, product.getProduct_id());
			
			int result=psmt.executeUpdate();
			if(result > 0) {
				System.out.println("Product updated Successfully");
			}else {
				System.out.println("Product Not Found");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void viewProduct() {
		Connection con=dbConnection.getDbConnection();
		
		try {
			PreparedStatement ps=con.prepareStatement(view);
			
			ResultSet rs=ps.executeQuery();
			
			System.out.println("----------------Product List--------------------------");
			while(rs.next()) {
				System.out.println("Product Id   : "+rs.getInt("product_id"));
				System.out.println("Product Name : "+rs.getString("product_name"));
				System.out.println("Description  :"+rs.getString("discription"));
				System.out.println("Price        :"+rs.getDouble("price"));
				System.out.println("Quantity     :"+rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void searchProduct() {
		Scanner scan=new Scanner(System.in);
		
		System.out.println("------------------Search Products------------------");
		System.out.println("Enter product name or keyword to search ");
		
		String keyword=scan.nextLine();
		
		Connection con=dbConnection.getDbConnection();
		
		try {
			PreparedStatement ps=con.prepareStatement(search);
			
			String searchKeyword ="%"+keyword+"%";
			
			ps.setString(1, searchKeyword);
			ps.setString(2, searchKeyword);
			
			ResultSet rs=ps.executeQuery();
			
			boolean found=false;
			
			System.out.println("showing matching products:");
			System.out.println("product Id | Name | Description | Price | Quantity");
			System.out.println("---------------------------------------------------");
			
			while(rs.next()) {
				
				found=true;
				
				System.out.println(rs.getInt("product_id")+ " | " +rs.getString("product_name")+" | "+rs.getString("discription")+"|"+rs.getDouble("price")+"|"+rs.getInt("quantity"));
			}
			if(!found) {
				System.out.println("No matching products found ");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	
}
