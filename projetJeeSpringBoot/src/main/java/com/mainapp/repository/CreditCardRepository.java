package com.mainapp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.CreditCard;

/**
 * The repository interface for managing CreditCard entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>{

    /**
     * Updates the credit of a credit card by deducting the specified price.
     *
     * @param cardNumber The card number of the credit card.
     * @param price      The price to deduct from the credit.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Credit_card SET credit = credit - :price WHERE card_number = :cardNumber", nativeQuery = true)
	int useCredit(@Param("cardNumber") int cardNumber, @Param("price") double price);

    /**
     * Deletes credit cards that have expired.
     *
     * @param currentDate The current date to compare against.
     */
	@Transactional
    @Modifying
	@Query(value = "DELETE FROM Credit_card WHERE expiration_date < :currentDate", nativeQuery = true)
	void deleteExpiredCard(@Param("currentDate") Date currentDate);

    /**
     * Retrieves a credit card by its card number.
     *
     * @param cardNumber The card number of the credit card.
     * @return The credit card associated with the specified card number.
     */
	@Query(value = "SELECT * FROM Credit_card WHERE card_number = :cardNumber", nativeQuery = true)
	CreditCard getCreditCard(@Param("cardNumber") int cardNumber);

    /**
     * Retrieves a credit card by its card number, CVV, and expiration date.
     *
     * @param cardNumber The card number of the credit card.
     * @param cvv        The CVV of the credit card.
     * @param date       The expiration date of the credit card.
     * @return The credit card associated with the specified card number, CVV, and expiration date.
     */
	@Query(value = "SELECT * FROM Credit_card WHERE card_number = :cardNumber AND cvv = :cvv AND expiration_date = :date", nativeQuery = true)
	CreditCard getCreditCard(@Param("cardNumber") int cardNumber, @Param("cvv") int cvv, @Param("date") Date date);

    /**
     * Adds credit to a credit card.
     *
     * @param cardNumber The card number of the credit card.
     * @param credit     The credit to add to the credit card.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Credit_card SET credit = credit - :credit WHERE card_number = :cardNumber", nativeQuery = true)
	int addCredit(@Param("cardNumber") int cardNumber, @Param("credit") double credit);
}
