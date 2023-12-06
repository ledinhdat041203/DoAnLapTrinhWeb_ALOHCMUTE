package com.hcmute.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Repository.UserInfoResponsitory;

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



	public Optional<UserInfoEntity> findById(Long id) {
		return user_info.findById(id);
	}





	
	
	
}
