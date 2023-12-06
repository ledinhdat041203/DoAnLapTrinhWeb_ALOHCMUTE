package vn.hcmute.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.UserEntity;


public interface PasswordTokenResponsitory extends JpaRepository<ResetPasswordEntity, Long>{
	Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user);
    void deleteByUserResetPass(UserEntity user);
    ResetPasswordEntity findByToken(String token);
    
}
