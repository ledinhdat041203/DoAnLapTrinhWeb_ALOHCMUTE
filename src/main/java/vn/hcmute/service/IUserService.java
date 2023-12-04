package vn.hcmute.service;

import java.util.Optional;

import vn.hcmute.entities.UserEntity;

public interface IUserService {


	Boolean checkLogin(String Email,String pass);

	Optional<UserEntity> findByemailContaining(String name);

	<S extends UserEntity> S save(S entity);

	
	


}
