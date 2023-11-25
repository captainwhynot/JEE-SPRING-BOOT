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

/**
 * The repository interface for managing Basket entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    /**
     * Updates the quantity of a basket.
     *
     * @param id       The ID of the basket to update.
     * @param quantity The new quantity to set.
     * @return The number of affected rows in the database.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE Basket SET quantity = :quantity WHERE id = :id", nativeQuery = true)
    int updateQuantity(@Param("id") int id, @Param("quantity") int quantity);

    /**
     * Retrieves a list of baskets for a given customer with unpurchased items.
     *
     * @param customerId The ID of the customer.
     * @return The list of baskets with unpurchased items for the specified customer.
     */
    @Query(value = "SELECT * FROM Basket WHERE customer_id = :customerId AND purchase_date IS NULL", nativeQuery = true)
    List<Basket> getBasketList(@Param("customerId") int customerId);

    /**
     * Retrieves a list of baskets containing a specific product.
     *
     * @param productId The ID of the product.
     * @return The list of baskets containing the specified product.
     */
    @Query(value = "SELECT * FROM Basket WHERE product_id = :productId", nativeQuery = true)
    List<Basket> getBasketListProduct(@Param("productId") int productId);

    /**
     * Retrieves a basket by its ID.
     *
     * @param id The ID of the basket.
     * @return The basket associated with the provided ID.
     */
    @Query(value = "SELECT * FROM Basket WHERE id = :id", nativeQuery = true)
    Basket getBasket(@Param("id") int id);

    /**
     * Retrieves a basket for a specific customer and product with unpurchased items.
     *
     * @param customerId The ID of the customer.
     * @param productId  The ID of the product.
     * @return The basket with unpurchased items for the specified customer and product.
     */
    @Query(value = "SELECT * FROM Basket WHERE product_id = :productId AND purchase_date IS NULL AND customer_id = :customerId", nativeQuery = true)
    Basket getBasket(@Param("customerId") int customerId, @Param("productId") int productId);

    /**
     * Confirms an order by retrieving baskets with unpurchased items for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return The list of baskets with unpurchased items for the specified customer.
     */
    @Query(value = "SELECT * FROM Basket WHERE customer_id = :customerId AND purchase_date IS NULL AND quantity > 0", nativeQuery = true)
    List<Basket> confirmOrder(@Param("customerId") int customerId);

    /**
     * Updates the purchase date of baskets for a specific customer with unpurchased items.
     *
     * @param customerId The ID of the customer.
     * @param date       The date to set as the purchase date.
     * @return The number of affected rows in the database.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE Basket SET purchase_date = :date WHERE customer_id = :customerId AND purchase_date IS NULL AND quantity > 0", nativeQuery = true)
    int putInHistory(@Param("customerId") int customerId, @Param("date") Date date);

    /**
     * Retrieves the stock of a product associated with a basket.
     *
     * @param id The ID of the basket.
     * @return The stock of the product associated with the specified basket.
     */
    @Query(value = "SELECT stock FROM Product p JOIN Basket b ON p.id = b.product_id WHERE b.id = :id", nativeQuery = true)
    Integer getStock(@Param("id") int id);

    /**
     * Retrieves a list of baskets with purchased items for a specific customer.
     *
     * @param id The ID of the customer.
     * @return The list of baskets with purchased items for the specified customer.
     */
    @Query(value = "SELECT * FROM Basket WHERE customer_id = :id AND purchase_date IS NOT NULL;", nativeQuery = true)
    List<Basket> getHistoryList(@Param("id") int id);

    /**
     * Deletes a basket by its ID.
     *
     * @param id The ID of the basket to delete.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Basket WHERE id = :id")
    void deleteBasket(@Param("id") int id);
}
