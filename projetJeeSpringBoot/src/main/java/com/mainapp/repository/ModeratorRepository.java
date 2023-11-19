package com.mainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mainapp.entity.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer>{
		
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id", nativeQuery = true)
    List<Moderator> getModeratorList() ;
	
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id WHERE u.id= :id", nativeQuery = true)
    Moderator getModerator(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Moderator m JOIN User u ON m.id = u.id WHERE u.email= :email", nativeQuery = true)
    Moderator getModerator(@Param("email") String email);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET add_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int addRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);

	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET modify_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int modifyRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);

	@Transactional
    @Modifying
    @Query(value = "UPDATE Moderator SET delete_product = :bool WHERE id= :#{#moderator.id}", nativeQuery = true)
    int deleteRight(@Param("moderator") Moderator moderator, @Param("bool") int bool);
}
