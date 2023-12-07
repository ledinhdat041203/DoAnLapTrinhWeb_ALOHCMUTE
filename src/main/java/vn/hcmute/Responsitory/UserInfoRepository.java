package vn.hcmute.Responsitory;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.UserInfoEntity;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>{
	Optional<UserInfoEntity> findByphoneNumberContaining(String phoneNumber);

	Optional<UserInfoEntity> findByuserIDContaining(Long userID);

	Optional<UserInfoEntity> findByUserIDEquals(Long userID);
	
	List<UserInfoEntity> findByFullNameContaining(String name);
}
