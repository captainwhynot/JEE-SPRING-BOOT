package com.mainapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;
import com.mainapp.entity.Product;
import com.mainapp.entity.User;
import com.mainapp.repository.ModeratorRepository;

/**
 * Service class for managing Moderator entities.
 * Handles interactions between the application and the ModeratorRepository.
 */
@Service
public class ModeratorService {

	private ModeratorRepository mr;
    private UserService us;
    private ProductService ps;

    /**
     * Sets the ModeratorRepository, UserService, and ProductService dependencies for the service.
     *
     * @param mr The ModeratorRepository to be injected.
     * @param us The UserService to be injected.
     * @param ps The ProductService to be injected.
     */
	@Autowired
	public void setDependencies(ModeratorRepository mr, UserService us, ProductService ps) {
		this.mr = mr;
		this.us = us;
		this.ps = ps;
	}

    /**
     * Retrieves the ModeratorRepository associated with this service.
     *
     * @return The ModeratorRepository associated with this service.
     */
	public ModeratorRepository getMr() {
		return mr;
	}

    /**
     * Retrieves a Moderator entity by its email.
     *
     * @param email The email of the Moderator.
     * @return The Moderator object associated with the given email.
     */
	public Moderator getModerator(String email) {
		return mr.getModerator(email);
	}

    /**
     * Retrieves a Moderator entity by its ID.
     *
     * @param id The ID of the Moderator.
     * @return The Moderator object associated with the given ID.
     */
	public Moderator getModerator(int id) {
		return mr.getModerator(id);
	}

    /**
     * Retrieves a list of all Moderator entities.
     *
     * @return A list of all Moderator entities.
     */
	public List<Moderator> getModeratorList(){
		return mr.findAll();
	}

    /**
     * Modifies the rights of a Moderator.
     *
     * @param moderator The Moderator object for which rights are to be modified.
     * @param right     The right to be modified ("add_product", "modify_product", or "delete_product").
     * @param bool      The boolean value representing the new state of the right.
     * @return True if the operation is successful, false otherwise.
     */
	public boolean modifyRight(Moderator moderator, String right, boolean bool) {
		int update = 0;
		try {
			switch (right) {
				case "add_product" :
					update = mr.addRight(moderator, bool?1:0);
					break;
				case "modify_product" :
					update = mr.modifyRight(moderator, bool?1:0);
					break;
				case "delete_product" :
					update = mr.deleteRight(moderator, bool?1:0);
					break;
				default:
					return false;
			}
			return (update > 0);
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Transfers a Moderator into a Customer role.
     *
     * @param moderator The Moderator to be transferred into a Customer.
     * @param savePath  The path to save associated files during the transfer.
     * @return True if the transfer is successful, false otherwise.
     */
	public boolean transferIntoCustomer(Moderator moderator, String savePath) {
		Customer customer = new Customer(moderator.getEmail(), moderator.getPassword(), moderator.getUsername());
	    boolean delete = deleteModerator(moderator, savePath);
	    boolean save = us.saveUser(customer);
		return (delete && save);
	}

    /**
     * Deletes a Moderator along with associated User and Products.
     *
     * @param moderator The Moderator to be deleted.
     * @param savePath  The path to save associated files during the deletion.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteModerator(Moderator moderator, String savePath) {
		try {
	        User user = us.getUser(moderator.getId());
	        List<Product> products = ps.getSellerProducts(moderator.getId());

	        // Delete the moderator & the user & the moderator's products
	        for (Product product : products) {
		        ps.deleteProduct(product.getId(), savePath);
	        }
	        mr.delete(moderator);
	        if (user != null) {
		        us.getUr().delete(user);
	        }
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
