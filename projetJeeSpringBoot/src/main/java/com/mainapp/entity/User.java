package com.mainapp.entity;

import jakarta.persistence.*;


import org.hibernate.annotations.DiscriminatorOptions;

import java.io.Serializable;
import java.util.List;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorOptions(force = true)
@DiscriminatorColumn(name = "TYPE_USER", discriminatorType = DiscriminatorType.STRING)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	private String password;
	private String username;
	//private String typeUser;
	private String profilePicture;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Moderator moderator;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Administrator admin;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
	
	public User() {
		
	}
	
	public User(String email, String password, String username, String typeUser) {
		this.email = email;
		this.password = password;
		this.username = username;
		//this.typeUser = typeUser;
		this.profilePicture = "img/profilePicture.png";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*public String getTypeUser() {
		return typeUser;
	}*/

	/*public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}*/

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}
