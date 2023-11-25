package com.mainapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;
import com.mainapp.entity.User;
import com.mainapp.repository.CustomerRepository;

/**
 * Service class for managing Customer entities.
 * Handles interactions between the application and the CustomerRepository.
 */
@Service
public class CustomerService {
	
    private CustomerRepository cr;
    private UserService us;
    private BasketService bs;

    /**
     * Sets the CustomerRepository, UserService, and BasketService dependencies for the service.
     *
     * @param cr The CustomerRepository to be injected.
     * @param us The UserService to be injected.
     * @param bs The BasketService to be injected.
     */
	@Autowired
	public void setDependencies(CustomerRepository cr, UserService us, BasketService bs) {
		this.cr = cr;
		this.us = us;
		this.bs = bs;
	}

    /**
     * Retrieves the CustomerRepository associated with this service.
     *
     * @return The CustomerRepository associated with this service.
     */
	public CustomerRepository getCr() {
		return cr;
	}

    /**
     * Retrieves a Customer entity by its email.
     *
     * @param email The email of the Customer.
     * @return The Customer object associated with the given email.
     */
	public Customer getCustomer(String email) {
		return cr.getCustomer(email);
	}

    /**
     * Retrieves a Customer entity by its ID.
     *
     * @param id The ID of the Customer.
     * @return The Customer object associated with the given ID.
     */
	public Customer getCustomer(int id) {
		return cr.getCustomer(id);
	}

    /**
     * Retrieves a list of all Customer entities.
     *
     * @return A list of all Customer entities.
     */
	public List<Customer> getCustomerList(){
		return cr.findAll();
	}

    /**
     * Adds or subtracts fidelity points for a customer.
     *
     * @param customer The Customer object for which fidelity points are to be updated.
     * @param points   The number of fidelity points to be added (positive) or subtracted (negative).
     * @return True if the operation is successful, false otherwise.
     */
	public boolean setFidelityPoint(Customer customer, double points) {
		int update = 0;
		if (points >= 0) {
			update = cr.addFidelityPoint(customer, points);
		} else {
			update = cr.useFidelityPoint(customer, Math.abs(points));
		}
		return (update > 0);
	}

    /**
     * Transfers a Customer into a Moderator role.
     *
     * @param customer The Customer to be transferred into a Moderator.
     * @return True if the transfer is successful, false otherwise.
     */
	public boolean transferIntoModerator(Customer customer) {
		Moderator moderator = new Moderator(customer.getEmail(), customer.getPassword(), customer.getUsername());
	    boolean delete = deleteCustomer(customer);
	    boolean save = us.saveUser(moderator);
		return (delete && save);
	}

    /**
     * Deletes a Customer along with associated User and Baskets.
     *
     * @param customer The Customer to be deleted.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteCustomer(Customer customer) {
		try {
	        User user = customer.getUser();
	        List<Basket> baskets = customer.getBaskets();

	        // Delete the customer & the user & the customer's basket
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
