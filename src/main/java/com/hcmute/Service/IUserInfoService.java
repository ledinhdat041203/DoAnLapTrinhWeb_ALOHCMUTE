package com.hcmute.Service;

import java.util.Optional;

import com.hcmute.Entity.UserInfoEntity;


public interface IUserInfoService {

	Optional<UserInfoEntity> findById(Long id);

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	<S extends UserInfoEntity> S save(S entity);

}
