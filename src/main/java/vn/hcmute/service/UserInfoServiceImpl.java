package vn.hcmute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import vn.hcmute.Responsitory.UserInfoRepository;
import vn.hcmute.entities.UserInfoEntity;


@Service
public class UserInfoServiceImpl implements IUserInfoService{
	@Autowired
	UserInfoRepository userinfoRepository;
	
	@Override
	public List<UserInfoEntity> findAll() {
		return userinfoRepository.findAll();
	}


//	@Override
//	public Optional<UserInfoEntity> findById(Long id) {
//	    if (id != null) {
//	        return userinfoRepository.findById(id).orElse(null);
//	    } else {
//	        return null;
//	    }
//	}
	
	@Override
	public <S extends UserInfoEntity> S save(S entity) {
		return userinfoRepository.save(entity);
	}

	@Override
	public Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber) {
		return userinfoRepository.findByphoneNumberContaining(phoneNumber);
	}

	@Override
	public Optional<UserInfoEntity> findByuserIDContaining(Long userID) {
		return userinfoRepository.findByuserIDContaining((Long) userID);
	}

	@Override
	public Optional<UserInfoEntity> findByUserIDEquals(Long userID) {
		return userinfoRepository.findByUserIDEquals(userID);
	}

	@Override
	public Optional<UserInfoEntity> findById(Long id) {
		return userinfoRepository.findById(id);
	}	
	
}
