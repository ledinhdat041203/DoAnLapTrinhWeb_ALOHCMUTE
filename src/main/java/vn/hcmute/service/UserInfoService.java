package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.UserInfoRepository;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.UserInfoModel;


@Service
public class UserInfoService implements IUserInfoService{
	@Autowired
	UserInfoRepository user_info;

	public UserInfoService(UserInfoRepository user_info) {
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
	public List<UserInfoEntity> findAll() {
		
		return user_info.findAll();
	}

	public List<UserInfoEntity> findByFullNameContaining(String name) {
		return user_info.findByFullNameContaining(name);
	}
	
	@Override
	public UserInfoModel convertToUserInfoModel(UserInfoEntity user)
	{
		UserInfoModel userModel = new UserInfoModel();
		userModel.setUserID(user.getUserID());
		userModel.setFullName(user.getFullName());
		userModel.setDateOfBirth(user.getDateOfBirth());
		userModel.setAvata(user.getAvata());
		userModel.setAddress(user.getAddress());
		userModel.setPhoneNumber(user.getPhoneNumber());
		
		return userModel;
	}
	
}
