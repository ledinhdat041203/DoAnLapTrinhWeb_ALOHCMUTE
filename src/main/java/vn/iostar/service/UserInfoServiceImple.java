package vn.iostar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iostar.Responsitory.UserInfoResponsitory;
import vn.iostar.entities.UserInfoEntity;
@Service
public class UserInfoServiceImple implements IUserInfoService{
	@Autowired
	UserInfoResponsitory user_info;

	public UserInfoServiceImple(UserInfoResponsitory user_info) {
		this.user_info = user_info;
	}

	@Override
	public <S extends UserInfoEntity> S save(S entity) {
		return user_info.save(entity);
	}

	@Override
	public Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber) {
		return user_info.findByphoneNumberContaining(phoneNumber);
	}

	@Override
	public Optional<UserInfoEntity> findByuserIDContaining(Long userID) {
		return user_info.findByuserIDContaining(userID);
	}

	@Override
	public Optional<UserInfoEntity> findByUserIDEquals(Long userID) {
		return user_info.findByUserIDEquals(userID);
	}
	
	
	
	
}
