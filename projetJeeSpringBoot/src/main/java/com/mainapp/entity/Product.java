package com.mainapp.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	private int stock;
	private String img;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User user;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Basket> baskets;
	
	public Product() {
	}
	public Product(String name, double price, int stock, String img, User user) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.img = img;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
    
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}	
}
