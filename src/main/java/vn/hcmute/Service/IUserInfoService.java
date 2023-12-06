package vn.hcmute.Service;

import java.util.Optional;

import vn.hcmute.Entity.UserInfoEntity;


public interface IUserInfoService {

	Optional<UserInfoEntity> findById(Long id);

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	<S extends UserInfoEntity> S save(S entity);

}
