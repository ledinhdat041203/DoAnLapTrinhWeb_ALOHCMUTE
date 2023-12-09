package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.StatusAccountEntity;
import vn.hcmute.entities.UserEntity;

public interface IUserService {

	boolean isTokenExpired(ResetPasswordEntity pass);

	boolean isTokenFound(ResetPasswordEntity pass);


	void deleteById(Long id);

	String validToken(String token);

	void deleteByUserResetPass(UserEntity user);

	List<ResetPasswordEntity> findAll();


	Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user);


	void createToken(UserEntity user, String token);

	long count();

	<S extends UserEntity> S save(S entity);

	Boolean checkLogin(String Email, String pass);

	Optional<UserEntity> findByemailContaining(String name);

	void changePass(UserEntity user, String pass);

	void createCode(StatusAccountEntity status, int code);

	<S extends StatusAccountEntity> S save(S entity);

	Optional<UserEntity> findById(Long id);

	Optional<StatusAccountEntity> findByuserCode(UserEntity user);

	Optional<StatusAccountEntity> findBycode(int code);

	ResetPasswordEntity findByToken(String token);

	UserEntity findByUserInfoId(Long userId);

}
