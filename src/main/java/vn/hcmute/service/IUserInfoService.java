package vn.hcmute.service;

import java.util.Optional;
import java.util.List;
import vn.hcmute.entities.UserInfoEntity;


public interface IUserInfoService {

	<S extends UserInfoEntity> S save(S entity);

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	Optional<UserInfoEntity> findByuserIDContaining(Long userID);

	Optional<UserInfoEntity> findByUserIDEquals(Long userID);
	
	Optional<UserInfoEntity> findById(Long id);

	List<UserInfoEntity> findByFullNameContaining(String name);


}
