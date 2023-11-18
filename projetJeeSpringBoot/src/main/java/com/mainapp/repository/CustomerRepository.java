package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Administrator;
import com.mainapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query(value = "SELECT COUNT(*)>0 FROM Customer c JOIN User u ON c.id = u.id WHERE u.email = :#{#customer.email}", nativeQuery = true)
    boolean checkCustomer(Customer customer);
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id", nativeQuery = true)
    List<Customer> getCustomerList();
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.email= :email", nativeQuery = true)
    Customer getCustomer(String email);
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= :idCustomer", nativeQuery = true)
    Customer getCustomer(int idCustomer);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelityPoint = fidelityPoint +:points WHERE id= :#{#customer.id}", nativeQuery = true)
    void setFidelityPoint(Customer customer, int points);
	
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO Customer (id,fidelityPoint) VALUES (:#{#customer.id} , :#{#customer.fidelityPoint})", nativeQuery = true)
    void addCustomer(Customer Customer);
	
	//transfer into moderator
	//delete customer
}
