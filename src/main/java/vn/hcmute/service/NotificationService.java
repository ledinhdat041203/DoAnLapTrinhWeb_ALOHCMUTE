package vn.hcmute.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.NotificationRepository;
import vn.hcmute.entities.NotificationEntity;
import vn.hcmute.entities.UserInfoEntity;

@Service
public class NotificationService implements INotificationService {
	@Autowired
	NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public <S extends NotificationEntity> S save(S entity) {
		return notificationRepository.save(entity);
	}

	@Override
	public void createNotification(UserInfoEntity user2, String link, String content, String image) {
		long currentSQLDate = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentSQLDate);

		NotificationEntity newNotification = new NotificationEntity();
		newNotification.setTimeNotify(currentTimestamp);
		newNotification.setUser(user2);
		newNotification.setStatus(false);

		newNotification.setContent(content);
		newNotification.setLink(link);
		newNotification.setImage(image);

		notificationRepository.save(newNotification);
	}

	@Override
	public List<NotificationEntity> findByUserUserID(long userid) {
		Sort sortByTimeDesc = Sort.by(Sort.Direction.DESC, "timeNotify");
		return notificationRepository.findByUserUserID(userid, sortByTimeDesc);
	}
	
	
}