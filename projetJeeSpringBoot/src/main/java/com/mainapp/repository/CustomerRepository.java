package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Administrator;
import com.mainapp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	/*@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= 2", nativeQuery = true)
    Customer getCustomer(int idCustomer);
	
	Customer findById(int id);
	
	@Query("SELECT * FROM Customer c")
	List<Customer> findAll();*/
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.email = :#{#customer.email}", nativeQuery = true)
	Customer checkCustomer(@Param("customer") Customer customer);

	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id", nativeQuery = true)
    List<Customer> getCustomerList();
	
	@Query("SELECT c FROM Customer c JOIN User u ON c.id = u.id WHERE u.email= :email")
	Customer getCustomerByEmail(@Param("email") String email);

	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= :idCustomer", nativeQuery = true)
    Customer getCustomer(@Param("idCustomer")int idCustomer);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelityPoint = fidelityPoint +:points WHERE id= :#{#customer.id}", nativeQuery = true)
    void setFidelityPoint(@Param("customer")Customer customer, @Param("points")int points);
	
	
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO Customer (id,fidelityPoint) VALUES (:#{#customer.id} , :#{#customer.fidelityPoint})", nativeQuery = true)
    void addCustomer(@Param("customer")Customer customer);
	
	//transfer into moderator
	//delete customer
}
