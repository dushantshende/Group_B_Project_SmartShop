package com.ds.model;

public class User {

	
	private Integer user_id;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private String city;
	private String email;
	private Long mobile;
	//private Enum role;
	//Default 0-param constructor
	
	public User() {
		System.out.println("User.User()--0-param constructor");
	}
	
	//Adding Getters and setters
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	
	
	// Adding Parameterized constructor 
	
	public User(Integer user_id, String first_name, String last_name, String username, String password, String city,String email,
			Long mobile) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.city = city;
		this.email=email;
		this.mobile = mobile;
	}
	
	
	
}
