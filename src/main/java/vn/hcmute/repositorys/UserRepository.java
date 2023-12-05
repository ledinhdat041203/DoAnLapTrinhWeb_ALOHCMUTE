package vn.hcmute.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hcmute.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
