package com.mainapp.entity;

import java.util.Date;

import jakarta.persistence.*;

/**
 * The Basket class represents a basket containing a specific quantity of a product for a customer.
 * It is used to manage customer orders before the final purchase.
 */
@Entity
public class Basket {

    /**
     * The unique identifier for the basket.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    /**
     * The quantity of the product in the basket.
     */
	private int quantity;

    /**
     * The date when the basket is purchased (null if not purchased).
     */
	private Date purchaseDate;

    /**
     * The product associated with the basket.
     */
	@ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    /**
     * The customer who owns the basket.
     */
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    /**
     * Default constructor for the Basket class.
     */
	public Basket() {
	}

    /**
     * Parameterized constructor to create a Basket with the specified product, quantity, and customer.
     *
     * @param product  The product in the basket.
     * @param quantity The quantity of the product in the basket.
     * @param customer The customer who owns the basket.
     */
	public Basket(Product product, int quantity, Customer customer) {
		this.product = product;
		this.quantity = quantity;
		this.purchaseDate = null;
		this.customer = customer;
	}

    /**
     * Gets the unique identifier for the basket.
     *
     * @return The unique identifier for the basket.
     */
	public int getId() {
		return id;
	}

    /**
     * Gets the quantity of the product in the basket.
     *
     * @return The quantity of the product in the basket.
     */
	public int getQuantity() {
		return quantity;
	}

    /**
     * Gets the product associated with the basket.
     *
     * @return The product associated with the basket.
     */
	public Product getProduct() {
		return product;
	}

    /**
     * Gets the date when the basket is purchased.
     *
     * @return The date when the basket is purchased (null if not purchased).
     */
	public Date getPurchaseDate() {
		return purchaseDate;
	}
}
