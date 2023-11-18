package com.mainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.CreditCard;
import com.mainapp.entity.Customer;
import com.mainapp.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer>{
	
	//Test findById(int id);
	@Query(value="SELECT * FROM Test WHERE id = :id", nativeQuery = true)
    Test testById(@Param("id") int id);
	
	@Query(value="SELECT * FROM Test WHERE id = :#{#test.id}", nativeQuery = true)
    Test findByTestObject(@Param("test") Test test);
	
	@Transactional
	@Modifying
    @Query(value="UPDATE Test SET name = :name, price = :price WHERE id = :#{#test.id}", nativeQuery = true)
    void updateTest(@Param("test") Test test, @Param("price") double price, @Param("name") String name);
}

