package vn.iostar.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entities.UserInfoEntity;

public interface UserInfoResponsitory extends JpaRepository<UserInfoEntity, Long>{

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);
}
