package com.mainapp.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Basket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantity;
	private Date purchaseDate;
	@ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    
	public Basket() {
	}
	
	public Basket(Product product, int quantity, Customer customer) {
		this.product = product;
		this.quantity = quantity;
		this.setPurchaseDate(null);
		this.customer = customer;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date date) {
		this.purchaseDate = date;
	}
}
