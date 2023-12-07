package vn.hcmute.Responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.hcmute.entities.MessagesEntity;
import vn.hcmute.entities.UserInfoEntity;




public interface MessageRepository extends JpaRepository<MessagesEntity, Long> {
	@Query("SELECT u.userInfo FROM UserEntity u WHERE u.userInfo.userID = :conId")
	UserInfoEntity receiver(@Param("conId") Long receiverId);
}
