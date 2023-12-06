package vn.hcmute.Service;

import org.springframework.stereotype.Service;

import vn.hcmute.Entity.NotificationEntity;
import vn.hcmute.Entity.UserInfoEntity;

@Service
public interface INotificationService {

	<S extends NotificationEntity> S save(S entity);

	void createNotification(UserInfoEntity user2, String link, String content);
	
}
