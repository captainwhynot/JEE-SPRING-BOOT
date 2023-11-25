package com.mainapp.entity;

import java.util.Date;

import jakarta.persistence.*;

@SuppressWarnings("unused")
/**
 * The CreditCard class represents a credit card entity with essential attributes such as card number,
 * CVV, expiration date, and available credit.
 */
@Entity
public class CreditCard {

    /**
     * The unique card number associated with the credit card.
     */
	@Id
	private int cardNumber;

    /**
     * The available credit on the credit card.
     */
	private double credit;

    /**
     * The CVV (Card Verification Value) code of the credit card.
     */
	private int cvv;

    /**
     * The expiration date of the credit card.
     */
	private Date expirationDate;

    /**
     * Default constructor for the CreditCard class.
     */
	public CreditCard() {
	}

    /**
     * Parameterized constructor to create a CreditCard with the specified card number, CVV, and expiration date.
     *
     * @param cardNumber     The unique card number to set for the credit card.
     * @param cvv            The CVV (Card Verification Value) to set for the credit card.
     * @param expirationDate The expiration date to set for the credit card.
     */
	public CreditCard (int cardNumber, int cvv, Date expirationDate) {
		this.credit = 0;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expirationDate = expirationDate;
	}

    /**
     * Gets the card number associated with the credit card.
     *
     * @return The card number associated with the credit card.
     */
	public int getCardNumber() {
		return cardNumber;
	}

    /**
     * Gets the expiration date of the credit card.
     *
     * @return The expiration date of the credit card.
     */
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * Gets the available credit for the credit card.
	 *
	 * @return The available credit for the credit card.
     */
	public double getCredit() {
		return credit;
	}
	
	/**
     * Sets the available credit for the credit card.
     *
     * @param credit The available credit to set for the credit card.
     */
	public void setCredit(double credit) {
		this.credit = credit;
	}
}
