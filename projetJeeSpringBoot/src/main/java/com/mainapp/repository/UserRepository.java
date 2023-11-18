package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Moderator;
import com.mainapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = "SELECT *, 0 AS clazz_ FROM User WHERE email = :#{#user.email}", nativeQuery = true)
    User getUserMail(@Param("id") User user);
	
	@Query(value = "SELECT * FROM User WHERE id = :moderatorId", nativeQuery = true)
    User getUser(@Param("moderatoId") int moderatorId);
	
	@Query(value = "SELECT * FROM User WHERE email= :email", nativeQuery = true)
	User getUser(@Param("email") String email);
	
	@Query(value = "SELECT * FROM User WHERE email= :#{#user.email}", nativeQuery = true)
	List<User> checkMailUser(@Param("user") User user);
	
	
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE User SET email = :email, username = :username , password = :password WHERE id= :#{#user.id} ", nativeQuery = true)
    void updateUser(@Param("user")User user, @Param("email")String email, @Param("username")String username,@Param("password")String password);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE User SET profilePicture='img/Profil/:profilePicture WHERE id= :#{#user.id}", nativeQuery = true)
    void updateProfilePicturer(@Param("user") User user, @Param("profilePicture")String profilePicture);
    
	
	
	
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO User (id,email,password,profilePicture,typeUser,username) VALUES (:#{#user.id} , :#{#user.password}, :#{#user.typeUser}, :#{#user.username})", nativeQuery = true)
    void addUser(@Param("user")User user);
	
	
}
