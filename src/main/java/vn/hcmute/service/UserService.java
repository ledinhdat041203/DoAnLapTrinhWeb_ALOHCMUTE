package vn.hcmute.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.UserResponsitory;
import vn.hcmute.entities.UserEntity;

@Service
public class UserService implements IUserService{
	@Autowired
	UserResponsitory userResponsitory;

	public UserService(UserResponsitory userResponsitory) {
		this.userResponsitory = userResponsitory;
	}

	@Override
	public Optional<UserEntity> findByemailContaining(String name) {
		return userResponsitory.findByemailContaining(name);
	}

	@Override
	public Boolean checkLogin(String Email, String pass) {
		Optional<UserEntity> u = findByemailContaining(Email);
		if (u.isPresent()&&u.get().getPass().equals(pass))
			return true;
		return false;
	}

	@Override
	public <S extends UserEntity> S save(S entity) {
		return userResponsitory.save(entity);
	}

	@Override
	public long count() {
		return userResponsitory.count();
	}


	
	
	
	

	

	
}
