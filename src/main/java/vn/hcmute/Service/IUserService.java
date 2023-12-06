package vn.hcmute.Service;

import java.util.List;

import vn.hcmute.Entity.UserEntity;


public interface IUserService {

	
	
	Boolean checkLogin(String Email,String pass);

	List<UserEntity> findByemailContaining(String name);

	<S extends UserEntity> S save(S entity);

	


}
