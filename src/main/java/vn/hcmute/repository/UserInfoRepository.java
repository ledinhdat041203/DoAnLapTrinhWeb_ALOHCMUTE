package vn.hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.UserInfoEntity;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>{

}
