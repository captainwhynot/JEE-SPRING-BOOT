package com.mainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mainapp.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>{

}
