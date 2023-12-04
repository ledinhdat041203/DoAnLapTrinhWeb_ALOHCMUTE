package vn.hcmute.service;

import org.springframework.stereotype.Service;

import vn.hcmute.entities.UserInfoEntity;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserInfoService {

	List<UserInfoEntity> findAll();

	UserInfoEntity save(UserInfoEntity entity);

	UserInfoEntity findById(Long id);


	
	
}
