package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Product;

/**
 * The repository interface for managing Product entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    /**
     * Modifies the details of a product in the database.
     *
     * @param product The product to be modified.
     * @param name    The new name of the product.
     * @param price   The new price of the product.
     * @param stock   The new stock quantity of the product.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET name = :name, price = :price , stock = :stock WHERE id = :#{#product.id}", nativeQuery = true)
	int modifyProduct(@Param("product") Product product, @Param("name") String name, @Param("price") double price, @Param("stock") int stock);

    /**
     * Updates the image file name for a product in the database.
     *
     * @param id       The ID of the product.
     * @param fileName The new file name of the product image.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET img = :fileName WHERE id = :id", nativeQuery = true)
	int updateProductImg(@Param("id") int id, @Param("fileName") String fileName);

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product associated with the specified ID.
     */
    @Query(value = "SELECT * FROM Product WHERE id = :id", nativeQuery = true)
	Product getProduct(@Param("id") int id);

    /**
     * Retrieves a list of products associated with a seller ID.
     *
     * @param id The ID of the seller.
     * @return The list of products associated with the specified seller ID.
     */
    @Query(value = "SELECT * FROM Product WHERE seller_id= :id", nativeQuery = true)
	List<Product> getSellerProducts(@Param("id") int id);

    /**
     * Retrieves a list of products based on a search string (name).
     *
     * @param search The search string for product names.
     * @return The list of products matching the search criteria.
     */
    @Query(value = "SELECT * FROM Product WHERE name LIKE %:search%", nativeQuery = true)
	List<Product> getProductByName(@Param("search") String search);

    /**
     * Updates the stock quantity for a product in the database.
     *
     * @param id    The ID of the product.
     * @param stock The new stock quantity.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET stock = :stock WHERE id = :id", nativeQuery = true)
	int updateStock(@Param("id") int id, @Param("stock") int stock);

    /**
     * Deletes a product from the database.
     *
     * @param id The ID of the product to be deleted.
     */
	@Transactional
    @Modifying
    @Query("DELETE FROM Product WHERE id = :id")
    void deleteProduct(@Param("id") int id);
}
