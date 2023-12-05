package vn.hcmute.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hcmute.entities.UserInfoEntity;

@Service
public interface IUserInfoService {

	Optional<UserInfoEntity> findById(Long id);

	<S extends UserInfoEntity> S save(S entity);

}
