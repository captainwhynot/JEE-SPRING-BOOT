package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id", nativeQuery = true)
    List<Customer> getCustomerList();
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.email= :email", nativeQuery = true)
	Customer getCustomer(@Param("email") String email);

	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= :id", nativeQuery = true)
    Customer getCustomer(@Param("id") int id);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelity_point = fidelity_point + :points WHERE id= :#{#customer.id}", nativeQuery = true)
    int addFidelityPoint(@Param("customer") Customer customer, @Param("points") double points);

	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelity_point = fidelity_point - :points WHERE id= :#{#customer.id}", nativeQuery = true)
    int useFidelityPoint(@Param("customer") Customer customer, @Param("points") double points);
	
	@Query(value = "SELECT fidelity_point FROM Customer WHERE id= :id", nativeQuery = true)
	double getFidelityPoint(@Param("id") int id);
}
