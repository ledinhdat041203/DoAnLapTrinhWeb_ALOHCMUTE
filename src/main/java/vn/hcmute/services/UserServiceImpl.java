package vn.hcmute.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.repositorys.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public long count() {
		return userRepository.count();
	}
	
	
}
