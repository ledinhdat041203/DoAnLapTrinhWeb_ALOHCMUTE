package com.hcmute.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.UserEntity;
import com.hcmute.Repository.UserResponsitory;


@Service
public class UserServiceImple implements IUserService{
	@Autowired
	UserResponsitory userResponsitory;

	public UserServiceImple(UserResponsitory userResponsitory) {
		this.userResponsitory = userResponsitory;
	}

	@Override
	public List<UserEntity> findByemailContaining(String name) {
		return userResponsitory.findByemailContaining(name);
	}
	
	@Override
	public Boolean checkLogin(String email, String pass) {
	    List<UserEntity> list = findByemailContaining(email);
	    System.out.println(email);
	    System.out.println(list.size());
	    if (!list.isEmpty() ) {
	        UserEntity u = list.get(0);
	        System.out.println(u.getEmail());
	        System.out.println(u.getPass());

	        // Use equals for string comparison
	        if (u.getPass().equals(pass)) {
	            return true;
	        }
	    }

	    return false;
	}


	@Override
	public <S extends UserEntity> S save(S entity) {
		return userResponsitory.save(entity);
	}


	
	
	
	

	

	
}
