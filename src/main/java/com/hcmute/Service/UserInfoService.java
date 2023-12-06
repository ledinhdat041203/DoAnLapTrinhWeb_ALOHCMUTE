package com.hcmute.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Repository.UserInfoRepository;

@Service
public class UserInfoService implements IUserInfoService{
	@Autowired
	UserInfoRepository userInfoRepo;

	@Override
	public Optional<UserInfoEntity> findById(Long id) {
		return userInfoRepo.findById(id);
	}

	@Override
	public Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UserInfoEntity> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
