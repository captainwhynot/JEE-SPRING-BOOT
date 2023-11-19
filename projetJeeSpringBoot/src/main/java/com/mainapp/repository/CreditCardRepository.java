package com.mainapp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>{

	@Transactional
    @Modifying
    @Query(value = "UPDATE Credit_card SET credit = credit - :price WHERE card_number = :cardNumber", nativeQuery = true)
	int useCredit(@Param("cardNumber") int cardNumber, @Param("price") double price);

	@Transactional
    @Modifying
	@Query(value = "DELETE FROM Credit_card WHERE expiration_date < :currentDate", nativeQuery = true)
	void deleteExpiredCard(@Param("currentDate") Date currentDate);

	@Query(value = "SELECT * FROM Credit_card WHERE card_number = :cardNumber", nativeQuery = true)
	CreditCard getCreditCard(@Param("cardNumber") int cardNumber);

	@Query(value = "SELECT * FROM Credit_card WHERE card_number = :cardNumber AND cvv = :cvv AND expiration_date = :date", nativeQuery = true)
	CreditCard getCreditCard(@Param("cardNumber") int cardNumber, @Param("cvv") int cvv, @Param("date") Date date);

	@Transactional
    @Modifying
    @Query(value = "UPDATE Credit_card SET credit = credit - :credit WHERE card_number = :cardNumber", nativeQuery = true)
	int addCredit(@Param("cardNumber") int cardNumber, @Param("credit") double credit);
}
