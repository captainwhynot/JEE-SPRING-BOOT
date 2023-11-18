package com.mainapp.entity;

import java.io.Serializable;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Transient
	@OneToOne
    @JoinColumn(name = "id")
    private User user;

	public Administrator() {
		super();
	}
	
	public Administrator(String email, String password, String username) {
		super(email, password, username, "Administrator");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

