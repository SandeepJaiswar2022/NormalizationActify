package com.actify.NormalizationDemo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.actify.NormalizationDemo.Model.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    
    
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE AppUser SET role = :role WHERE email = :email", nativeQuery = true)
	void updateUserRole(@Param("email") String email, @Param("role") String role );
}
