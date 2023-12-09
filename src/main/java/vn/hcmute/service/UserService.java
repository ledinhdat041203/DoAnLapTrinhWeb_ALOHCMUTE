package vn.hcmute.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.PasswordTokenResponsitory;
import vn.hcmute.Responsitory.UserResponsitory;
import vn.hcmute.Responsitory.VerifyCodeResponsitory;
import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.StatusAccountEntity;
import vn.hcmute.entities.UserEntity;

@Service
public class UserService implements IUserService {
	@Autowired
	UserResponsitory userResponsitory;
	@Autowired
	PasswordTokenResponsitory passwordTokenResponsitory;
	@Autowired
	VerifyCodeResponsitory verifyResponsitory;
	public UserService(UserResponsitory userResponsitory) {
		this.userResponsitory = userResponsitory;
	}

	public UserService(PasswordTokenResponsitory passwordTokenResponsitory) {
		this.passwordTokenResponsitory = passwordTokenResponsitory;
	}

	public UserService(VerifyCodeResponsitory verifyResponsitory) {
		this.verifyResponsitory = verifyResponsitory;
	}

	public UserService() {
		// Constructor mặc định
	}


	@Override
	public Optional<UserEntity> findByemailContaining(String name) {
		return userResponsitory.findByemailContaining(name);
	}


	@Override
	public Boolean checkLogin(String Email, String pass) {
		Optional<UserEntity> u = findByemailContaining(Email);
		if (u.isPresent() && u.get().getPass().equals(pass))
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

	public void createToken(UserEntity user, String token) {
		ResetPasswordEntity reset = new ResetPasswordEntity(token, user);
		passwordTokenResponsitory.save(reset);
	}

	@Override
	public void createCode(StatusAccountEntity status, int code)
	{
		status.setCode(code);
		verifyResponsitory.save(status);
	}

	@Override
	public void deleteById(Long id) {
		passwordTokenResponsitory.deleteById(id);
	}


	@Override
	public Optional<ResetPasswordEntity> findByUserResetPass(UserEntity user) {
		return passwordTokenResponsitory.findByUserResetPass(user);
	}


	@Override
	public List<ResetPasswordEntity> findAll() {
		return passwordTokenResponsitory.findAll();
	}


	@Override
	public void deleteByUserResetPass(UserEntity user) {
		passwordTokenResponsitory.deleteByUserResetPass(user);
	}

	@Override
	public String validToken(String token)
	{
		ResetPasswordEntity pass = passwordTokenResponsitory.findByToken(token);
		return !isTokenFound(pass) ? "invalidToken"
	            : isTokenExpired(pass) ? "expired"
	            : null;
	}

	@Override
	public boolean isTokenFound(ResetPasswordEntity pass) {
	    return pass != null;
	}

	@Override
	public boolean isTokenExpired(ResetPasswordEntity pass) {
	    final Calendar cal = Calendar.getInstance();
	    return pass.getExpireDate().before(cal.getTime());
	}


	@Override
	public ResetPasswordEntity findByToken(String token) {
		return passwordTokenResponsitory.findByToken(token);
	}

	@Override
	public void changePass(UserEntity user,String pass)
	{
		user.setPass(pass);
		userResponsitory.save(user);
	}


	@Override
	public <S extends StatusAccountEntity> S save(S entity) {
		return verifyResponsitory.save(entity);
	}

	@Override
	public Optional<UserEntity> findById(Long id) {
		return userResponsitory.findById(id);
	}

	@Override
	public Optional<StatusAccountEntity> findByuserCode(UserEntity user) {
		return verifyResponsitory.findByuserCode(user);
	}

	@Override
	public Optional<StatusAccountEntity> findBycode(int code) {
		return verifyResponsitory.findBycode(code);
	}
	
	@Override
	public UserEntity findByUserInfoId(Long userId)
	{
		return userResponsitory.findByUserInfoUserID(userId);
	}
}

