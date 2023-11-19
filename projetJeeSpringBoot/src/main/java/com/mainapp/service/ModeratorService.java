package com.mainapp.service;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;
import com.mainapp.entity.Product;
import com.mainapp.entity.User;
import com.mainapp.repository.ModeratorRepository;

@Service
public class ModeratorService {

	private ModeratorRepository mr;
    private UserService us;
    private ProductService ps;

	@Autowired
	public void setDependencies(ModeratorRepository mr, UserService us, ProductService ps) {
		this.mr = mr;
		this.us = us;
		this.ps = ps;
	}

	public ModeratorRepository getMr() {
		return mr;
	}

	public Moderator getModerator(String email) {
		return mr.getModerator(email);
	}
	
	public Moderator getModerator(int id) {
		return mr.getModerator(id);
	}
	
	public List<Moderator> getModeratorList(){
		return mr.findAll();
	}
	
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
	
	public boolean transferIntoCustomer(Moderator moderator) {
		Customer customer = new Customer(moderator.getEmail(), BCrypt.hashpw(moderator.getPassword(), BCrypt.gensalt(12)), moderator.getUsername());
	    boolean delete = deleteModerator(moderator);
	    boolean save = us.saveUser(customer);
		return (delete && save);
	}
	
	public boolean deleteModerator(Moderator moderator) {
		try {
	        User user = moderator.getUser();
	        moderator.setUser(null);
	        List<Product> products = moderator.getProducts();

	        //Delete the moderator & the user & the moderator's products
	        mr.delete(moderator);
	        if (user != null) {
	            us.getUr().delete(user);
	        }
	        for (Product product : products) {
	        	ps.getPr().delete(product);
	        }
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
