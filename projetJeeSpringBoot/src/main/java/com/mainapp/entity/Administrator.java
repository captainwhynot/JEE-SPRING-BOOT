package com.mainapp.entity;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * The Administrator class represents an administrator user in the system.
 * Administrators have additional privileges for managing the application.
 */
@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends User implements Serializable {

    /**
     * The default serial version ID for serialization.
     */
	private static final long serialVersionUID = 1L;

    /**
     * The associated User entity for the Administrator.
     */
	@Transient
	@OneToOne
    @JoinColumn(name = "id")
    private User user;

    /**
     * Default constructor for the Administrator class.
     */
	public Administrator() {
		super();
	}

    /**
     * Parameterized constructor to create an Administrator with the specified email, password, and username.
     *
     * @param email    The email of the Administrator.
     * @param password The password of the Administrator.
     * @param username The username of the Administrator.
     */
	public Administrator(String email, String password, String username) {
		super(email, password, username, "Administrator");
	}
	
    /**
     * Gets the user information associated with the administrator.
     *
     * @return The user information associated with the administrator.
     */
	public User getUser() {
		return user;
	}
}

