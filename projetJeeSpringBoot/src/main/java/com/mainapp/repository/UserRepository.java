package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.User;

/**
 * The repository interface for managing User entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return The user associated with the specified ID.
     */
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE id = :id", nativeQuery = true)
    User getUser(@Param("id") int id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user associated with the specified email address.
     */
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE email= :email", nativeQuery = true)
	User getUser(@Param("email") String email);

    /**
     * Checks if a user with the given email address exists.
     *
     * @param email The email address to check.
     * @return The list of users with the specified email address.
     */
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE email= :email", nativeQuery = true)
	List<User> checkMailUser(@Param("email") String email);

    /**
     * Retrieves a list of all users excluding administrators.
     *
     * @return The list of all non-administrator users.
     */
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE type_user != 'Administrator'", nativeQuery = true)
	List<User> getAllNoAdministrator();

    /**
     * Updates the details of a user in the database.
     *
     * @param user     The user to be updated.
     * @param email    The new email address of the user.
     * @param username The new username of the user.
     * @param password The new password of the user.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE User SET email = :email, username = :username , password = :password WHERE id = :#{#user.id} ", nativeQuery = true)
    int updateUser(@Param("user") User user, @Param("email") String email, @Param("username") String username, @Param("password") String password);

    /**
     * Updates the profile picture of a user in the database.
     *
     * @param id             The ID of the user.
     * @param profilePicture The new profile picture file name.
     * @return The number of affected rows in the database.
     */
	@Transactional
	@Modifying
	@Query(value = "UPDATE User SET profile_picture = :profilePicture WHERE id = :id", nativeQuery = true)
	int updateProfilePicture(@Param("id") int id, @Param("profilePicture") String profilePicture);
}
