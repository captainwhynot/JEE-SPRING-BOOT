package com.mainapp.entity;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * The Moderator class represents a moderator entity with attributes such as permissions to
 * add, modify, and delete products, along with user information.
 */
@Entity
@DiscriminatorValue("Moderator")
public class Moderator extends User implements Serializable {

    /**
     * The default serial version ID for serialization.
     */
	private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the moderator has permission to add products.
     */
	private boolean addProduct;

    /**
     * Indicates whether the moderator has permission to modify products.
     */
	private boolean modifyProduct;

    /**
     * Indicates whether the moderator has permission to delete products.
     */
	private boolean deleteProduct;

    /**
     * The user information associated with the moderator.
     */
	@Transient
	@OneToOne
    @JoinColumn(name = "id")
    private User user;

    /**
     * Default constructor for the Moderator class.
     */
	public Moderator() {
		super();
	}

    /**
     * Parameterized constructor to create a Moderator with the specified email, password, and username.
     *
     * @param email    The email to set for the moderator.
     * @param password The password to set for the moderator.
     * @param username The username to set for the moderator.
     */
	public Moderator(String email, String password, String username) {
		super(email, password, username, "Moderator");
		this.addProduct = false;
		this.modifyProduct = false;
		this.deleteProduct = false;
	}

    /**
     * Checks if the moderator has permission to add products.
     *
     * @return True if the moderator can add products, false otherwise.
     */
	public boolean canAddProduct() {
		return addProduct;
	}

    /**
     * Checks if the moderator has permission to modify products.
     *
     * @return True if the moderator can modify products, false otherwise.
     */
	public boolean canModifyProduct() {
		return modifyProduct;
	}

    /**
     * Checks if the moderator has permission to delete products.
     *
     * @return True if the moderator can delete products, false otherwise.
     */
	public boolean canDeleteProduct() {
		return deleteProduct;
	}

    /**
     * Gets the user information associated with the moderator.
     *
     * @return The user information associated with the moderator.
     */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user information associated with the moderator.
	 *
	 * @param user The user to be associated with the moderator.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}

