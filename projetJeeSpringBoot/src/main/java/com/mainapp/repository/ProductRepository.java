package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET name = :name, price = :price , stock = :stock WHERE id = :#{#product.id}", nativeQuery = true)
	int modifyProduct(@Param("product") Product product, @Param("name") String name, @Param("price") double price, @Param("stock") int stock);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET img = :fileName WHERE id = :id", nativeQuery = true)
	int updateProductImg(@Param("id") int id, @Param("fileName") String fileName);

    @Query(value = "SELECT * FROM Product WHERE id = :id", nativeQuery = true)
	Product getProduct(@Param("id") int id);
    
    @Query(value = "SELECT * FROM Product WHERE seller_id= :id", nativeQuery = true)
	List<Product> getSellerProducts(@Param("id") int id);
    
    @Query(value = "SELECT * FROM Product WHERE name LIKE %:search%", nativeQuery = true)
	List<Product> getProductByName(@Param("search") String search);
    
	@Transactional
    @Modifying
    @Query(value = "UPDATE Product SET stock = :stock WHERE id = :id", nativeQuery = true)
	int updateStock(@Param("id") int id, @Param("stock") int stock);
}
