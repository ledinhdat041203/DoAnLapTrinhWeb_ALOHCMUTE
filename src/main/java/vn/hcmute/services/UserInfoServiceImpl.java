package vn.hcmute.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.repositorys.UserInfoRepository;

@Service
public class UserInfoServiceImpl implements IUserInfoService{
	@Autowired
	UserInfoRepository userInfoRepository;

	public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public <S extends UserInfoEntity> S save(S entity) {
		return userInfoRepository.save(entity);
	}

	@Override
	public Optional<UserInfoEntity> findById(Long id) {
		return userInfoRepository.findById(id);
	}
	
}
