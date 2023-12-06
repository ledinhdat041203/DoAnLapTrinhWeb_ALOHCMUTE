package com.hcmute.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.Entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>{
	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);
}
