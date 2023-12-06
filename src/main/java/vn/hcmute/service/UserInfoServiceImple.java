package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.UserInfoRepository;
import vn.hcmute.entities.UserInfoEntity;


@Service
public class UserInfoServiceImple implements IUserInfoService{
	@Autowired
	UserInfoRepository user_info;

	public UserInfoServiceImple(UserInfoRepository user_info) {
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

	@Override
	public Optional<UserInfoEntity> findById(Long id) {
		
		return user_info.findById(id);
	}

	@Override
	public List<UserInfoEntity> findByFullNameContaining(String name) {
		return user_info.findByFullNameContaining(name);
	}

	
	
	
	
	
}
