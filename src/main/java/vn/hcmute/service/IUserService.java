package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.UserEntity;

public interface IUserService {

	void changePass(UserEntity user, String pass);

	ResetPasswordEntity findByToken(String token);

	boolean isTokenExpired(ResetPasswordEntity pass);

	boolean isTokenFound(ResetPasswordEntity pass);

	String validToken(String token);

	void deleteByUserResetPass(UserEntity user);

	List<ResetPasswordEntity> findAll();

	Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user);

	void deleteById(Long id);

	void createToken(UserEntity user, String token);

	long count();

	<S extends UserEntity> S save(S entity);

	Boolean checkLogin(String Email, String pass);

	Optional<UserEntity> findByemailContaining(String name);

	

}
