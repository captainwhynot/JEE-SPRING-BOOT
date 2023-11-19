package com.mainapp.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.CreditCard;
import com.mainapp.repository.CreditCardRepository;


@Service
public class CreditCardService {

    private CreditCardRepository ccr;

	@Autowired
    public void setDependencies(CreditCardRepository ccr) {
    	this.ccr = ccr;
    }
    
	public CreditCardRepository getCcr() {
		return ccr;
	}
	
	public boolean saveCreditCard(CreditCard card) {
		try {
			deleteExpiredCard();
			ccr.save(card);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void deleteExpiredCard() {
		ccr.deleteExpiredCard(new Date());
	}
	
	public CreditCard getCreditCard(int cardNumber) {
		CreditCard card = ccr.getCreditCard(cardNumber);
		
		if (card != null && card.getExpirationDate().after(new Date())) {
			return card;
		} else {
			return null;
		}
	}
	
	public boolean checkCreditCard(int cardNumber, int cvv, Date date) {
		CreditCard card = ccr.getCreditCard(cardNumber, cvv, date);
        //Check if the credit card's is expired, and if the informations are invalid, catch the exception
		return (card != null && card.getExpirationDate().after(new Date()));
	}
	
	public boolean checkBalance(int cardNumber, double price) {
		double credit = ccr.getCreditCard(cardNumber).getCredit();
		return (credit - price > 0);
	}
	
	public boolean setCredit(int cardNumber, int credit) {
		int update = ccr.addCredit(cardNumber, credit);
		return (update > 0);
	}
	
	public boolean deleteCreditCard(int cardNumber) {
		try {
			ccr.delete(this.getCreditCard(cardNumber));
			return true;
		} catch (Exception e) {
	        return false;
	    }
	}
}
