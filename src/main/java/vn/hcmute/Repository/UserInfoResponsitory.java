package vn.hcmute.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hcmute.Entity.UserInfoEntity;


public interface UserInfoResponsitory extends JpaRepository<UserInfoEntity, Long>{

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);
}
