package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.UserInfoModel;

public interface IUserInfoService {

	<S extends UserInfoEntity> S save(S entity);

	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	Optional<UserInfoEntity> findByuserIDContaining(Long userID);

	Optional<UserInfoEntity> findByUserIDEquals(Long userID);
	
	Optional<UserInfoEntity> findById(Long id);
  
	List<UserInfoEntity> findAll();

	//UserInfoEntity findById(Long id);

	List<UserInfoEntity> findByFullNameContaining(String name);

	UserInfoModel convertToUserInfoModel(UserInfoEntity user);

}
