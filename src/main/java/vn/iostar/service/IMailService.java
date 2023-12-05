package vn.iostar.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import vn.iostar.entities.UserEntity;
import vn.iostar.model.EmailInfo;
@Service
public interface IMailService {
	void send(EmailInfo email) throws MessagingException;
	void send (String subject, String body, UserEntity user) throws MessagingException;
	void queue(EmailInfo email);
	void queue(String subject, String body, UserEntity user);
	void constructResetTokenEmail(String contextPath, String token, UserEntity user) throws MessagingException ;
}
