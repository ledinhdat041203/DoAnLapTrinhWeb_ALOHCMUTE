package vn.hcmute.service;

import org.springframework.stereotype.Service;

import vn.hcmute.entities.NotificationEntity;
import vn.hcmute.entities.UserInfoEntity;

@Service
public interface INotificationService {

	<S extends NotificationEntity> S save(S entity);

	void createNotification(UserInfoEntity user2, String link, String content);
	
}
