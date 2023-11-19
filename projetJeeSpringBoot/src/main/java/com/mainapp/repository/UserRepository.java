package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE id = :id", nativeQuery = true)
    User getUser(@Param("id") int id);
	
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE email= :email", nativeQuery = true)
	User getUser(@Param("email") String email);
	
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE email= :email", nativeQuery = true)
	List<User> checkMailUser(@Param("email") String email);
	
	
	@Query(value = "SELECT *,0 as fidelity_point, 0 as add_product, 0 as modify_product,0 as delete_product FROM User WHERE type_user != 'Administrator'", nativeQuery = true)
	List<User> getAllNoAdministrator();
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE User SET email = :email, username = :username , password = :password WHERE id = :#{#user.id} ", nativeQuery = true)
    int updateUser(@Param("user") User user, @Param("email") String email, @Param("username") String username, @Param("password") String password);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE User SET profile_picture = :profilePicture WHERE id = :id", nativeQuery = true)
	int updateProfilePicture(@Param("id") int id, @Param("profilePicture") String profilePicture);
}
