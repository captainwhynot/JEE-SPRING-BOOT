package com.mainapp.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class CreditCard {
	
	@Id
	private int cardNumber;
	private double credit;
	private int cvv;
	private Date expirationDate;
	
	public CreditCard() {
	}

	public CreditCard (int cardNumber, int cvv, Date expirationDate) {
		this.credit = 0;
		this.cardNumber= cardNumber;
		this.cvv=cvv;
		this.expirationDate = expirationDate;
	}
	
	public int getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public int getCvv() {
		return cvv;
	}
	
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}
}
