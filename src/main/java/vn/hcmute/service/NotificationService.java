package vn.hcmute.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.NotificationRepository;
import vn.hcmute.entities.NotificationEntity;
import vn.hcmute.entities.UserInfoEntity;

@Service
public class NotificationService implements INotificationService{
	@Autowired
	NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public <S extends NotificationEntity> S save(S entity) {
		return notificationRepository.save(entity);
	}
	
	public void createNotification(UserInfoEntity user2, String link, String content) {
		LocalDate localDate = LocalDate.now();
		Date currentDate = Date.valueOf(localDate);
		
		NotificationEntity newNotification = new NotificationEntity();
		newNotification.setDateNotify(currentDate);
		newNotification.setUser(user2);
		newNotification.setStatus(false);
		
		newNotification.setContent(content);
		newNotification.setLink(link);
		
		notificationRepository.save(newNotification);
	}
	
}
