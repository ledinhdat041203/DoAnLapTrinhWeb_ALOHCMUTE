package vn.hcmute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.repository.UserInfoRepository;

@Service
public class UserInfoServiceImpl implements IUserInfoService{
	@Autowired
	UserInfoRepository userinfoRepository;
	
	public UserInfoServiceImpl(UserInfoRepository userinfoRepository)
	{
		this.userinfoRepository= userinfoRepository;
	}


	@Override
	public UserInfoEntity save(UserInfoEntity entity) {
		return userinfoRepository.save(entity);
	}


	@Override
	public List<UserInfoEntity> findAll() {
		return userinfoRepository.findAll();
	}


	@Override
	public UserInfoEntity findById(Long id) {
	    if (id != null) {
	        return userinfoRepository.findById(id).orElse(null);
	    } else {
	        return null;
	    }
	}


	
	


	
	
}
