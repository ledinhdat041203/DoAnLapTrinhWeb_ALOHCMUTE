package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.UserEntity;

public interface IUserService {


	Boolean checkLogin(String Email,String pass);

	Optional<UserEntity> findByemailContaining(String name);

	<S extends UserEntity> S save(S entity);
	void createToken(UserEntity user,String token);

	long count();

	void deleteById(Long id);

	Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user);

	List<ResetPasswordEntity> findAll();

	void deleteByUserResetPass(UserEntity user);

	boolean isTokenExpired(ResetPasswordEntity pass);

	boolean isTokenFound(ResetPasswordEntity pass);

	String validToken(String token);

	ResetPasswordEntity findByToken(String token);

	void changePass(UserEntity user, String pass);
}
