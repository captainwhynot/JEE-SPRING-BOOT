package com.mainapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.CreditCard;
import com.mainapp.repository.CreditCardRepository;

/**
 * Service class for managing CreditCard entities.
 * Handles interactions between the application and the CreditCardRepository.
 */
@Service
public class CreditCardService {

    private CreditCardRepository ccr;

    /**
     * Sets the CreditCardRepository dependency for the service.
     *
     * @param ccr The CreditCardRepository to be injected.
     */
	@Autowired
    public void setDependencies(CreditCardRepository ccr) {
    	this.ccr = ccr;
    }

    /**
     * Retrieves the CreditCardRepository associated with this service.
     *
     * @return The CreditCardRepository associated with this service.
     */
	public CreditCardRepository getCcr() {
		return ccr;
	}

    /**
     * Saves a CreditCard entity to the repository and deletes expired cards.
     *
     * @param card The CreditCard to be saved.
     * @return True if the operation is successful, false otherwise.
     */
	public boolean saveCreditCard(CreditCard card) {
		try {
			deleteExpiredCard();
			ccr.save(card);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Deletes credit cards that have expired.
     */
	public void deleteExpiredCard() {
		ccr.deleteExpiredCard(new Date());
	}

    /**
     * Retrieves a CreditCard entity by its card number.
     *
     * @param cardNumber The card number of the CreditCard.
     * @return The CreditCard object associated with the given card number, or null if not found or expired.
     */
	public CreditCard getCreditCard(int cardNumber) {
		CreditCard card = ccr.getCreditCard(cardNumber);
		
		if (card != null && card.getExpirationDate().after(new Date())) {
			return card;
		} else {
			return null;
		}
	}

    /**
     * Checks the validity of a credit card based on card number, CVV, and expiration date.
     *
     * @param cardNumber The card number of the CreditCard.
     * @param cvv        The CVV of the CreditCard.
     * @param date       The expiration date of the CreditCard.
     * @return True if the credit card is valid, false otherwise.
     */
	public boolean checkCreditCard(int cardNumber, int cvv, Date date) {
		CreditCard card = ccr.getCreditCard(cardNumber, cvv, date);
        // Check if the credit card's is expired, and if the informations are invalid, catch the exception
		return (card != null && card.getExpirationDate().after(new Date()));
	}

    /**
     * Checks if the credit card has sufficient balance for a given price.
     *
     * @param cardNumber The card number of the CreditCard.
     * @param price      The price to be checked against the credit card's balance.
     * @return True if the balance is sufficient, false otherwise.
     */
	public boolean checkBalance(int cardNumber, double price) {
		double credit = ccr.getCreditCard(cardNumber).getCredit();
		return (credit - price > 0);
	}

    /**
     * Adds or subtracts credit from a credit card.
     *
     * @param cardNumber The card number of the CreditCard.
     * @param credit     The amount of credit to be added (positive) or subtracted (negative).
     * @return True if the operation is successful, false otherwise.
     */
	public boolean setCredit(int cardNumber, int credit) {
		int update = ccr.addCredit(cardNumber, credit);
		return (update > 0);
	}

    /**
     * Deletes a credit card from the repository.
     *
     * @param cardNumber The card number of the CreditCard to be deleted.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteCreditCard(int cardNumber) {
		try {
			ccr.delete(this.getCreditCard(cardNumber));
			return true;
		} catch (Exception e) {
	        return false;
	    }
	}
}
