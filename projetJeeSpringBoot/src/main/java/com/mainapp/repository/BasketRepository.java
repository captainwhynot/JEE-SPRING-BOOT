package com.mainapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Basket SET quantity = :quantity WHERE id = :id", nativeQuery = true)
    int updateQuantity(@Param("id") int id, @Param("quantity") int quantity);

    @Query(value = "SELECT * FROM Basket WHERE customer_id = :customerId AND purchase_date IS NULL", nativeQuery = true)
    List<Basket> getBasketList(@Param("customerId") int customerId);

    @Query(value = "SELECT * FROM Basket WHERE id = :id", nativeQuery = true)
    Basket getBasket(@Param("id") int id);

    @Query(value = "SELECT * FROM Basket WHERE product_id = :productId AND purchase_date IS NULL AND customer_id = :customerId", nativeQuery = true)
    Basket getBasket(@Param("customerId") int customerId, @Param("productId") int productId);

    @Query(value = "SELECT * FROM Basket WHERE customer_id = :customerId AND purchase_date IS NULL AND quantity > 0", nativeQuery = true)
    List<Basket> confirmOrder(@Param("customerId") int customerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Basket SET purchase_date = :date WHERE customer_id = :customerId AND purchase_date IS NULL AND quantity > 0", nativeQuery = true)
    int putInHistory(@Param("customerId") int customerId, @Param("date") Date date);

    @Query(value = "SELECT stock FROM Product p JOIN Basket b ON p.id = b.product_id WHERE b.id = :id", nativeQuery = true)
    Integer getStock(@Param("id") int id);
    
    @Query(value = "SELECT * FROM Basket WHERE customerId = :id AND purchaseDate IS NOT NULL;", nativeQuery = true)
    List<Basket> getHistoryList(@Param("id") int id);
}
