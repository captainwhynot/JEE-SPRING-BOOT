package com.mainapp.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("moderator")

public class Moderator extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean addProduct;
	private boolean modifyProduct;
	private boolean deleteProduct;
	@OneToOne
    @JoinColumn(name = "id")
    private User user;
	
	public Moderator() {
		super();
	}
	
	public Moderator(String email, String password, String username) {
		super(email, password, username, "Moderator");
		this.addProduct = false;
		this.modifyProduct = false;
		this.deleteProduct = false;
	}
	
	public boolean canAddProduct() {
		return addProduct;
	}
	
	public void setAddProduct(boolean addProduct) {
		this.addProduct = addProduct;
	}
	
	public boolean canModifyProduct() {
		return modifyProduct;
	}
	
	public void setModifyProduct(boolean modifyProduct) {
		this.modifyProduct = modifyProduct;
	}
	
	public boolean canDeleteProduct() {
		return deleteProduct;
	}
	
	public void setDeleteProduct(boolean deleteProduct) {
		this.deleteProduct = deleteProduct;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

