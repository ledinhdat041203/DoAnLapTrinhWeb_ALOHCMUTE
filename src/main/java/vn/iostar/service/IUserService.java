package vn.iostar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iostar.entities.UserEntity;

public interface IUserService {


	Boolean checkLogin(String Email,String pass);

	Optional<UserEntity> findByemailContaining(String name);

	<S extends UserEntity> S save(S entity);

	
	


}
