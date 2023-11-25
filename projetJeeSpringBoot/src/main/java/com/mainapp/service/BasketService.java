package com.mainapp.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Basket;
import com.mainapp.entity.Customer;
import com.mainapp.entity.User;
import com.mainapp.repository.BasketRepository;

@Service
public class BasketService {

    private BasketRepository br;
    private CustomerService cs;
    private CreditCardService ccs;
    private ProductService ps;
    private UserService us;

	@Autowired
	public void setDependencies(BasketRepository br, CustomerService cs, CreditCardService ccs, ProductService ps, UserService us) {
		this.br = br;
		this.cs = cs;
		this.ccs = ccs;
		this.ps = ps;
		this.us = us;
	}
	
	public BasketRepository getBr() {
		return br;
	}

	public boolean addOrder(Basket basket, int customerId, int quantity) {
		try {
			Basket oldBasket = br.getBasket(customerId, basket.getProduct().getId());
			//If the product is already in the basket, add quantity if there is enough stock
			if (checkStock(oldBasket.getId(), oldBasket.getQuantity()+quantity)) {
				return updateQuantity(oldBasket.getId(), oldBasket.getQuantity()+quantity);
			} else {
				return false;
			}
		} catch (NullPointerException npe) {
	        // Handle the case where no result is found (oldBasket is null)
	        try {
	        	br.save(basket);
	        	//If the product is out of stock, add it with quantity null
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
	
	public boolean updateQuantity(int id, int quantity) {
		//Add product only if there is stock left.
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
	
	public List<Basket> getBasketList(int customerId) {
		return br.getBasketList(customerId);
	}

	public List<Basket> getBasketListProduct(int productId) {
		return br.getBasketListProduct(productId);
	}
	
	public List<Basket> getHistoryList(int customerId) {
		return br.getHistoryList(customerId);
	}
	
	public Basket getBasket(int id) {
		return br.getBasket(id);
	}
	
	public List<Basket> confirmOrder(int customerId) {
		//Check the stock for the final order.
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

	public boolean finalizePaiement(int customerId, int cardNumber, double price, String mailContainer) {
		Customer customer = cs.getCr().getCustomer(customerId);
		double fidelityPoint = cs.getCr().getFidelityPoint(customerId);
	    //Use all the fidelity points available
	    double fidelityPointToUse = Math.min(fidelityPoint, price);
		
	    boolean numberFidelityPoint = cs.setFidelityPoint(customer, fidelityPointToUse + price/10);

		//Remove the price from credit card's credit
		int numberRowSolde = ccs.getCcr().useCredit(cardNumber, price - fidelityPointToUse);

		int numberRowProduct = 0;
		List<Basket> basketList = this.getBasketList(customerId);
		for (Basket basket : basketList) {
			//Update each product's stock
			numberRowProduct = ps.getPr().updateStock(basket.getProduct().getId(), (basket.getProduct().getStock() - basket.getQuantity()) );
		}

		//Put the order in the history
		int numberRowBasket = br.putInHistory(customerId, new Date());
		
		User user = us.getUser(customerId);
		us.sendMail(user.getEmail(), "MANGASTORE : Paiement recapitulation", mailContainer);
		return (numberRowSolde > 0 && numberRowBasket > 0 && numberFidelityPoint && numberRowProduct > 0);
	}
	
	public boolean checkStock(int id, int quantity) {
		Integer stock = br.getStock(id);
		if (stock != null) return (stock >= quantity);
		else return false;
	}
	
	public double totalPrice(int customerId) {
		double totalPrice = 0;
		List<Basket> basketList = this.getBasketList(customerId);
		
		for (Basket basket : basketList) {
			totalPrice += basket.getProduct().getPrice() * basket.getQuantity();
		}
			
		return totalPrice;
	}

	public boolean deleteOrder(int id) {
		try {
			br.deleteBasket(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
