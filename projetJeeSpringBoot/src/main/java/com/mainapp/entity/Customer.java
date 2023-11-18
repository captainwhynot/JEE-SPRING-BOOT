package com.mainapp.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int fidelityPoint;
	@OneToOne
    @JoinColumn(name = "id")
    private User user;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Basket> baskets;


	public Customer() {
		super();
	}
	
	public Customer(String email, String password, String username) {
		super(email, password, username, "Customer");
		this.setFidelityPoint(0);
	}

	public int getFidelityPoint() {
		return fidelityPoint;
	}

	private void setFidelityPoint(int fidelityPoint) {
		this.fidelityPoint = fidelityPoint;
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
}

