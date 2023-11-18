//package com.mainapp.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.mainapp.entity.Administrator;
//
//@Repository
//public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
//	
//	@Query(value = "SELECT * FROM Administrator a JOIN User u ON a.id = u.id WHERE u.email = :email", nativeQuery = true)
//    List<Administrator> getAdministrator(String email);
//	
//	
//	/*@Transactional
//    @Modifying
//    @Query(value = "INSERT INTO Administrator (id) VALUES (:#{#user.id})", nativeQuery = true)
//    void addAdministrator(Administrator user);*/
//	
//	
//	
//	
//}
