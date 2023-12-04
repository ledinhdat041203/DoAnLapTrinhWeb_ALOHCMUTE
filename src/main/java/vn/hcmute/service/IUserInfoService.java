package vn.hcmute.service;

import org.springframework.stereotype.Service;

import vn.hcmute.entities.UserInfoEntity;

import java.util.List;
import java.util.Optional;

public interface IUserInfoService {

	<S extends UserInfoEntity> S save(S entity);

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	Optional<UserInfoEntity> findByuserIDContaining(Long userID);

	Optional<UserInfoEntity> findByUserIDEquals(Long userID);
	
	Optional<UserInfoEntity> findById(Long id);
  
  List<UserInfoEntity> findAll();

	UserInfoEntity save(UserInfoEntity entity);

	UserInfoEntity findById(Long id);
}
