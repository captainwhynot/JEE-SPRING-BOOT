package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Customer;
import com.mainapp.entity.Moderator;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer>{
	
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id WHERE u.email = :#{#moderator.email}", nativeQuery = true)
    boolean checkModerator(Moderator moderator);
	
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id", nativeQuery = true)
    List<Moderator> getModeratorList();
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.id= :moderatorId", nativeQuery = true)
    Moderator getModerator(int moderatorId);
	
	@Query(value = "SELECT * FROM Customer c JOIN User u ON c.id = u.id WHERE u.email= :email", nativeQuery = true)
    Moderator getModerator(String email);
	
	//transfer
	//delete
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET :right = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    void modifyRight(Moderator moderator, String right, boolean bool);
	
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO Moderator (id,addProduct,deleteProduct,modifyProduct) VALUES (:#{#moderator.id} , :#{#moderator.addProduct}, :#{#moderator.deleteProduct}, :#{#moderator.modifyProduct})", nativeQuery = true)
    void addModerator(Moderator moderator);
	
	
	
	
	
}
