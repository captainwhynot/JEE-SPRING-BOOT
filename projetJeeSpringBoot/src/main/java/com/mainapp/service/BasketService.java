package com.mainapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.User;
import com.mainapp.repository.BasketRepository;

/**
 * Service class for managing Basket entities.
 * Handles interactions between the application and the BasketRepository.
 */
@Service
public class BasketService {

    private BasketRepository br;
    private CustomerService cs;
    private CreditCardService ccs;
    private ProductService ps;
    private UserService us;

    /**
     * Sets the dependencies for the service.
     *
     * @param br  The BasketRepository to be injected.
     * @param cs  The CustomerService to be injected.
     * @param ccs The CreditCardService to be injected.
     * @param ps  The ProductService to be injected.
     * @param us  The UserService to be injected.
     */
	@Autowired
	public void setDependencies(BasketRepository br, CustomerService cs, CreditCardService ccs, ProductService ps, UserService us) {
		this.br = br;
		this.cs = cs;
		this.ccs = ccs;
		this.ps = ps;
		this.us = us;
	}

    /**
     * Retrieves the BasketRepository associated with this service.
     *
     * @return The BasketRepository associated with this service.
     */
	public BasketRepository getBr() {
		return br;
	}

    /**
     * Adds an order to the basket. If the product is already in the basket, adds the specified quantity.
     *
     * @param basket      The Basket to be added or updated.
     * @param customerId  The ID of the customer placing the order.
     * @param quantity    The quantity of the product to be added to the basket.
     * @return True if the operation is successful, false otherwise.
     */
	public boolean addOrder(Basket basket, int customerId, int quantity) {
		try {
			Basket oldBasket = br.getBasket(customerId, basket.getProduct().getId());
			// If the product is already in the basket, add quantity if there is enough stock
			if (checkStock(oldBasket.getId(), oldBasket.getQuantity()+quantity)) {
				return updateQuantity(oldBasket.getId(), oldBasket.getQuantity()+quantity);
			} else {
				return false;
			}
		} catch (NullPointerException npe) {
	        // Handle the case where no result is found (oldBasket is null)
	        try {
	        	br.save(basket);
	        	// If the product is out of stock, add it with quantity null
	        	if (!checkStock(basket.getId(), quantity)) {
	        		updateQuantity(basket.getId(), 0);
				}
		        return true;
	        } catch (Exception e) {
		        return false;
			}
	    } catch (Exception e) {
	        return false;
		}
	}

    /**
     * Updates the quantity of a product in the basket.
     *
     * @param id       The ID of the basket.
     * @param quantity The new quantity of the product in the basket.
     * @return True if the operation is successful, false otherwise.
     */
	public boolean updateQuantity(int id, int quantity) {
		// Add product only if there is stock left.
		if (checkStock(id, quantity)) {
			try {
				int update = br.updateQuantity(id, quantity);
				return (update > 0);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

    /**
     * Retrieves a list of baskets for a given customer ID.
     *
     * @param customerId The ID of the customer.
     * @return A list of baskets associated with the customer.
     */
	public List<Basket> getBasketList(int customerId) {
		return br.getBasketList(customerId);
	}

    /**
     * Retrieves a list of baskets containing a specific product.
     *
     * @param productId The ID of the product.
     * @return A list of baskets containing the specified product.
     */
	public List<Basket> getBasketListProduct(int productId) {
		return br.getBasketListProduct(productId);
	}

    /**
     * Retrieves a list of baskets representing the order history for a given customer ID.
     *
     * @param customerId The ID of the customer.
     * @return A list of baskets representing the order history for the customer.
     */
	public List<Basket> getHistoryList(int customerId) {
		return br.getHistoryList(customerId);
	}

    /**
     * Retrieves a basket by its ID.
     *
     * @param id The ID of the basket.
     * @return The Basket object associated with the given ID.
     */
	public Basket getBasket(int id) {
		return br.getBasket(id);
	}

    /**
     * Confirms the final order for a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of baskets representing the confirmed order.
     */
	public List<Basket> confirmOrder(int customerId) {
		// Check the stock for the final order.
		List<Basket> basketList = getBasketList(customerId);
		
		for (Basket basket : basketList) {
			if (!checkStock(basket.getId(), basket.getQuantity())) {
				int update = br.updateQuantity(basket.getId(), 0);
				if (update <= 0) return null;
			}
		}
		
		basketList = br.confirmOrder(customerId);
		return basketList;
	}

    /**
     * Finalizes the payment process for a customer's order.
     *
     * @param customerId   The ID of the customer.
     * @param cardNumber   The credit card number used for payment.
     * @param price        The total price of the order.
     * @param mailContainer The mail content for order recapitulation.
     * @return True if the payment process is successful, false otherwise.
     */
	public boolean finalizePaiement(int customerId, int cardNumber, double price, String mailContainer) {
		Customer customer = cs.getCr().getCustomer(customerId);
		double fidelityPoint = cs.getCr().getFidelityPoint(customerId);
	    // Use all the fidelity points available
	    double fidelityPointToUse = Math.min(fidelityPoint, price);
		
	    boolean numberFidelityPoint = cs.setFidelityPoint(customer, fidelityPointToUse + price/10);

		// Remove the price from credit card's credit
		int numberRowSolde = ccs.getCcr().useCredit(cardNumber, price - fidelityPointToUse);

		int numberRowProduct = 0;
		List<Basket> basketList = this.getBasketList(customerId);
		for (Basket basket : basketList) {
			// Update each product's stock
			numberRowProduct = ps.getPr().updateStock(basket.getProduct().getId(), (basket.getProduct().getStock() - basket.getQuantity()) );
		}

		// Put the order in the history
		int numberRowBasket = br.putInHistory(customerId, new Date());
		
		User user = us.getUser(customerId);
		us.sendMail(user.getEmail(), "MANGASTORE : Paiement recapitulation", mailContainer);
		return (numberRowSolde > 0 && numberRowBasket > 0 && numberFidelityPoint && numberRowProduct > 0);
	}

    /**
     * Checks if there is sufficient stock for a given basket.
     *
     * @param id       The ID of the basket.
     * @param quantity The desired quantity of the product.
     * @return True if there is sufficient stock, false otherwise.
     */
	public boolean checkStock(int id, int quantity) {
		Integer stock = br.getStock(id);
		if (stock != null) return (stock >= quantity);
		else return false;
	}

    /**
     * Calculates the total price of the products in a customer's basket.
     *
     * @param customerId The ID of the customer.
     * @return The total price of the products in the customer's basket.
     */
	public double totalPrice(int customerId) {
		double totalPrice = 0;
		List<Basket> basketList = this.getBasketList(customerId);
		
		for (Basket basket : basketList) {
			totalPrice += basket.getProduct().getPrice() * basket.getQuantity();
		}
			
		return totalPrice;
	}

    /**
     * Deletes a basket order.
     *
     * @param id The ID of the basket to be deleted.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteOrder(int id) {
		try {
			br.deleteBasket(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
