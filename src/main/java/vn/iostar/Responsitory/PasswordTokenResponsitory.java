package vn.iostar.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entities.ResetPasswordEntity;
import vn.iostar.entities.UserEntity;

public interface PasswordTokenResponsitory extends JpaRepository<ResetPasswordEntity, Long>{
	Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user);
    void deleteByUserResetPass(UserEntity user);
    ResetPasswordEntity findByToken(String token);
    
}
