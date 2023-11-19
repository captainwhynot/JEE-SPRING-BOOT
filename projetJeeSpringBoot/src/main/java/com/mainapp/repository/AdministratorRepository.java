package com.mainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainapp.entity.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	
	@Query(value = "SELECT * FROM Administrator a JOIN User u ON a.id = u.id WHERE u.id = :id", nativeQuery = true)
    Administrator getAdministrator(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Administrator a JOIN User u ON a.id = u.id WHERE u.email = :email", nativeQuery = true)
    Administrator getAdministrator(@Param("email") String email);
}
