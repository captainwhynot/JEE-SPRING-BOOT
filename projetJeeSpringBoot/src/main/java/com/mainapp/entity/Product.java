package com.mainapp.entity;

import java.util.List;

import jakarta.persistence.*;

/**
 * The Product class represents a product entity with attributes such as name,
 * price, stock, image, seller information, and associated baskets.
 */
@Entity
public class Product {

	/**
	 * The unique identifier for the product.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * The name of the product.
	 */
	private String name;

	/**
	 * The price of the product.
	 */
	private double price;

	/**
	 * The available stock of the product.
	 */
	private int stock;

	/**
	 * The image path of the product.
	 */
	private String img;

	/**
	 * The seller information associated with the product.
	 */
	@ManyToOne
	@JoinColumn(name = "sellerId")
	private User user;

	/**
	 * The list of baskets associated with the product.
	 */
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Basket> baskets;

	/**
	 * Default constructor for the Product class.
	 */
	public Product() {
	}

	/**
	 * Parameterized constructor to create a Product with the specified name, price,
	 * stock, image, and seller information.
	 *
	 * @param name  The name of the product.
	 * @param price The price of the product.
	 * @param stock The available stock of the product.
	 * @param img   The image path of the product.
	 * @param user  The seller information associated with the product.
	 */
	public Product(String name, double price, int stock, String img, User user) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.img = img;
		this.user = user;
	}

	/**
	 * Gets the unique identifier of the product.
	 *
	 * @return The unique identifier of the product.
	 */
	public int getId() {
		return id;
	}

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name for the product.
     *
     * @param name The name to set for the product.
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
	public double getPrice() {
		return price;
	}

    /**
     * Sets the price for the product.
     *
     * @param price The price to set for the product.
     */
	public void setPrice(double price) {
		this.price = price;
	}

    /**
     * Gets the available stock of the product.
     *
     * @return The available stock of the product.
     */
	public int getStock() {
		return stock;
	}

    /**
     * Sets the available stock for the product.
     *
     * @param stock The available stock to set for the product.
     */
	public void setStock(int stock) {
		this.stock = stock;
	}

    /**
     * Gets the seller information associated with the product.
     *
     * @return The seller information associated with the product.
     */
	public User getUser() {
		return user;
	}

    /**
     * Sets the seller information for the product.
     *
     * @param user The seller information to set for the product.
     */
	public void setUser(User user) {
		this.user = user;
	}

    /**
     * Gets the image path of the product.
     *
     * @return The image path of the product.
     */
	public String getImg() {
		return img;
	}
}
