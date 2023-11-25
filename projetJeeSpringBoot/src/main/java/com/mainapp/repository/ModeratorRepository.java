package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Moderator;

/**
 * The repository interface for managing Moderator entities in the database.
 * Extends JpaRepository for basic CRUD operations.
 */
@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer>{

    /**
     * Retrieves a list of all moderators with their associated user information.
     *
     * @return The list of moderators with their associated user information.
     */	
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id", nativeQuery = true)
    List<Moderator> getModeratorList() ;

    /**
     * Retrieves a moderator by their ID.
     *
     * @param id The ID of the moderator.
     * @return The moderator associated with the specified ID.
     */
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id WHERE u.id= :id", nativeQuery = true)
    Moderator getModerator(@Param("id") int id);

    /**
     * Retrieves a moderator by their email address.
     *
     * @param email The email address of the moderator.
     * @return The moderator associated with the specified email address.
     */
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id WHERE u.email= :email", nativeQuery = true)
    Moderator getModerator(@Param("email") String email);

    /**
     * Adds or removes the right to add products for a moderator.
     *
     * @param moderator The moderator for whom the right will be modified.
     * @param bool      The value (0 or 1) indicating whether to add or remove the right.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET add_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int addRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);

    /**
     * Adds or removes the right to modify products for a moderator.
     *
     * @param moderator The moderator for whom the right will be modified.
     * @param bool      The value (0 or 1) indicating whether to add or remove the right.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET modify_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int modifyRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);

    /**
     * Adds or removes the right to delete products for a moderator.
     *
     * @param moderator The moderator for whom the right will be modified.
     * @param bool      The value (0 or 1) indicating whether to add or remove the right.
     * @return The number of affected rows in the database.
     */
	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET delete_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int deleteRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);
}
