package com.mainapp.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

/**
 * The Customer class represents a customer entity with attributes such as fidelity points,
 * user information, and a list of baskets associated with the customer.
 */
@Entity
@DiscriminatorValue("Customer")
public class Customer extends User implements Serializable {

    /**
     * The default serial version ID for serialization.
     */
	private static final long serialVersionUID = 1L;

    /**
     * The fidelity points associated with the customer.
     */
	private double fidelityPoint;

    /**
     * The user information associated with the customer.
     */
	@Transient
	@OneToOne
    @JoinColumn(name = "id")
    private User user;

    /**
     * The list of baskets associated with the customer.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Basket> baskets;

    /**
     * Default constructor for the Customer class.
     */
	public Customer() {
		super();
	}

    /**
     * Parameterized constructor to create a Customer with the specified email, password, and username.
     *
     * @param email    The email to set for the customer.
     * @param password The password to set for the customer.
     * @param username The username to set for the customer.
     */
	public Customer(String email, String password, String username) {
		super(email, password, username, "Customer");
		this.fidelityPoint = 0;
	}

	/**
     * Gets the fidelity points associated with the customer.
     *
     * @return The fidelity points associated with the customer.
     */
	public double getFidelityPoint() {
		return fidelityPoint;
	}

    /**
     * Gets the user information associated with the customer.
     *
     * @return The user information associated with the customer.
     */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user information associated with the customer.
	 *
	 * @param user The user to be associated with the customer.
	 */
	public void setUser(User user) {
		this.user = user;
	}

    /**
     * Gets the list of baskets associated with the customer.
     *
     * @return The list of baskets associated with the customer.
     */
	public List<Basket> getBaskets() {
        return baskets;
    }
}

