package com.hcmute.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hcmute.Entity.UserEntity;


public interface UserResponsitory extends JpaRepository<UserEntity, Long>{
	@Query("SELECT u FROM UserEntity u WHERE u.email = 'alohcmute1@gmail.com'")
	List<UserEntity> findByemailContaining(@Param("eml") String email);
}
