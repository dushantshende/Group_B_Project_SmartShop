package com.ds.model;

public class Products {

	private Integer product_id;
	private String product_name;
	private String description;
	private Double price;
	private Integer quantity;
	
	
	
	public Products() {
		System.out.println("Products.Products()-0-param constructor");
	}
	
	//getters and setters
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Products(Integer product_id, String product_name, String description, Double price, Integer quantity) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}
	
	
	
}
