package com.ds.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	
	private String url="jdbc:mysql://localhost:3306/jdbc_db";
	private String username="root";
	private String password="root";
	private	Connection con;
	public Connection getDbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
			System.out.println("Connected to the database successfully!");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	
}
}