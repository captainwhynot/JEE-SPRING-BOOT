package com.mainapp.service;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;
import com.mainapp.entity.User;
import com.mainapp.repository.CustomerRepository;

@Service
public class CustomerService {
	
    private CustomerRepository cr;
    private UserService us;
    private BasketService bs;

	@Autowired
	public void setDependencies(CustomerRepository cr, UserService us, BasketService bs) {
		this.cr = cr;
		this.us = us;
		this.bs = bs;
	}

	public CustomerRepository getCr() {
		return cr;
	}
	
	public Customer getCustomer(String email) {
		return cr.getCustomer(email);
	}
	
	public Customer getCustomer(int id) {
		return cr.getCustomer(id);
	}
	
	public List<Customer> getCustomerList(){
		return cr.findAll();
	}
	
	public boolean setFidelityPoint(Customer customer, double points) {
		int update = 0;
		if (points >= 0) {
			update = cr.addFidelityPoint(customer, points);
		} else {
			update = cr.useFidelityPoint(customer, points);
		}
		return (update > 0);
	}
	
	public boolean transferIntoModerator(Customer customer) {
		Moderator moderator = new Moderator(customer.getEmail(), BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)), customer.getUsername());
	    boolean delete = deleteCustomer(customer);
	    boolean save = us.saveUser(moderator);
		return (delete && save);
	}
	
	public boolean deleteCustomer(Customer customer) {
		try {
	        User user = customer.getUser();
	        customer.setUser(null);
	        List<Basket> baskets = customer.getBaskets();

	        //Delete the customer & the user & the customer's basket
	        cr.delete(customer);
	        if (user != null) {
	            us.getUr().delete(user);
	        }
	        for (Basket basket : baskets) {
	        	bs.getBr().delete(basket);
	        }
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
