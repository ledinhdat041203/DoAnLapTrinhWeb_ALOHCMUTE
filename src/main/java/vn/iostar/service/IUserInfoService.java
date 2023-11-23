package vn.iostar.service;

import java.util.Optional;

import vn.iostar.entities.UserInfoEntity;

public interface IUserInfoService {

	<S extends UserInfoEntity> S save(S entity);

	
	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);
	

}
