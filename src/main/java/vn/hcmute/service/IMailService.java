package vn.hcmute.service;

import jakarta.mail.MessagingException;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.model.EmailInfo;


public interface IMailService {
	void send(EmailInfo email) throws MessagingException;
	void send (String subject, String body, UserEntity user) throws MessagingException;
	void queue(EmailInfo email);
	void queue(String subject, String body, UserEntity user);
	void constructResetTokenEmail(String contextPath, String token, UserEntity user) throws MessagingException ;
}
