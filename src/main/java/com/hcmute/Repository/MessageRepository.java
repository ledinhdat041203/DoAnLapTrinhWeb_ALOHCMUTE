package com.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hcmute.Entity.MessagesEntity;
import com.hcmute.Entity.UserInfoEntity;

public interface MessageRepository extends JpaRepository<MessagesEntity, Long> {
	@Query("SELECT u.userInfo FROM UserEntity u WHERE u.userInfo.userID = :conId")
	UserInfoEntity receiver(@Param("conId") Long receiverId);
}
