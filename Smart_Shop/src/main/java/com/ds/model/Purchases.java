package com.ds.model;

import java.util.Date;

public class Purchases {

	private Integer purchase_id;
	private Integer quantity;
	private Date date;
	
	public Purchases() {
		System.out.println("Purchases.Purchases()");
	}
	
	
	public Integer getPurchase_id() {
		return purchase_id;
	}
	public void setPurchase_id(Integer purchase_id) {
		this.purchase_id = purchase_id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Purchases(Integer purchase_id, Integer quantity, Date date) {
		super();
		this.purchase_id = purchase_id;
		this.quantity = quantity;
		this.date = date;
	}
	
	
}
