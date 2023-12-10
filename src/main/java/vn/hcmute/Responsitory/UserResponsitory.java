package vn.hcmute.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hcmute.entities.UserEntity;


public interface UserResponsitory extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByemailContaining(String email);
	
	
	UserEntity findByUserInfoUserID(Long userId);

}
