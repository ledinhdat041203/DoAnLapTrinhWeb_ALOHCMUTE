package vn.hcmute.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hcmute.entities.StatusAccountEntity;
import vn.hcmute.entities.UserEntity;

public interface VerifyCodeResponsitory extends JpaRepository<StatusAccountEntity, Long>{
	Optional<StatusAccountEntity> findByuserCode(UserEntity user);
	Optional<StatusAccountEntity> findBycode(int code);
}
