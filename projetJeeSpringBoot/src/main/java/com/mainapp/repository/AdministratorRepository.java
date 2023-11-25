package com.mainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainapp.entity.Administrator;

/**
 * The repository interface for managing Administrator entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	
	/**
     * Retrieves an administrator by their user ID.
     *
     * @param id The user ID of the administrator.
     * @return The administrator associated with the provided user ID.
     */
	@Query(value = "SELECT * FROM Administrator a JOIN User u ON a.id = u.id WHERE u.id = :id", nativeQuery = true)
    Administrator getAdministrator(@Param("id") int id);

    /**
     * Retrieves an administrator by their email address.
     *
     * @param email The email address of the administrator.
     * @return The administrator associated with the provided email address.
     */
	@Query(value = "SELECT * FROM Administrator a JOIN User u ON a.id = u.id WHERE u.email = :email", nativeQuery = true)
    Administrator getAdministrator(@Param("email") String email);
}
