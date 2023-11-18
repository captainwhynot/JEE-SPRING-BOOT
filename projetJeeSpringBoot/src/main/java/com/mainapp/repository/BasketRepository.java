package com.mainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mainapp.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer>{

}
