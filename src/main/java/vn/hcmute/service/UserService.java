package vn.hcmute.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.PasswordTokenResponsitory;
import vn.hcmute.Responsitory.UserResponsitory;
import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.UserEntity;


@Service
public class UserService implements IUserService{
	@Autowired
	UserResponsitory userResponsitory;
	
	@Autowired
	PasswordTokenResponsitory passwordTokenResponsitory;


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

	public long count() {
		return userResponsitory.count();
	}

	public void createToken(UserEntity user, String token) {
		ResetPasswordEntity reset = new ResetPasswordEntity(token, user);
		passwordTokenResponsitory.save(reset);
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
	

}

