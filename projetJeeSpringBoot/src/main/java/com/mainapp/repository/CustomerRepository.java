package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Customer;

/**
 * The repository interface for managing Customer entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    /**
     * Retrieves a list of all customers with their associated user information.
     *
     * @return The list of customers with their associated user information.
     */
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id", nativeQuery = true)
    List<Customer> getCustomerList();

    /**
     * Retrieves a customer by their email address.
     *
     * @param email The email address of the customer.
     * @return The customer associated with the specified email address.
     */
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.email= :email", nativeQuery = true)
	Customer getCustomer(@Param("email") String email);

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer.
     * @return The customer associated with the specified ID.
     */
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= :id", nativeQuery = true)
    Customer getCustomer(@Param("id") int id);

    /**
     * Adds fidelity points to a customer.
     *
     * @param customer The customer to whom fidelity points will be added.
     * @param points   The fidelity points to add.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelity_point = fidelity_point + :points WHERE id= :#{#customer.id}", nativeQuery = true)
    int addFidelityPoint(@Param("customer") Customer customer, @Param("points") double points);

    /**
     * Uses fidelity points for a customer.
     *
     * @param customer The customer whose fidelity points will be used.
     * @param points   The fidelity points to use.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Customer SET fidelity_point = fidelity_point - :points WHERE id= :#{#customer.id}", nativeQuery = true)
    int useFidelityPoint(@Param("customer") Customer customer, @Param("points") double points);

    /**
     * Retrieves the fidelity points of a customer.
     *
     * @param id The ID of the customer.
     * @return The fidelity points associated with the specified customer ID.
     */
	@Query(value = "SELECT fidelity_point FROM Customer WHERE id= :id", nativeQuery = true)
	double getFidelityPoint(@Param("id") int id);
}
