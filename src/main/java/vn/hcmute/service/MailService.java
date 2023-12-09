package vn.hcmute.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.model.EmailInfo;

@Service
public class MailService implements IMailService {

	List<EmailInfo> list = new ArrayList<>();
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void constructResetTokenEmail(String contextPath, String token, UserEntity user) throws MessagingException {
		String url = contextPath + "/user/changePassword?token=" + token;
		String message = "reset password";
		this.queue("Reset Password", message + " \r\n" + url, user);
	}

	@Override
	public void constructCreateCode(int code, UserEntity user) throws MessagingException {
		String message = "Code của bạn là: ";
		this.queue("Create Code", message + code, user);
	}

	public void send(EmailInfo email) throws MessagingException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setText(email.getBody(), true);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void send(String subject, String body, UserEntity user) throws MessagingException {
		EmailInfo mail = new EmailInfo();
		mail.setTo(user.getEmail());
		mail.setBody(body);
		mail.setSubject(subject);
		this.send(mail);
	}

	@Override
	public void queue(EmailInfo email) {
		list.add(email);

	}

	@Override
	public void queue(String subject, String body, UserEntity user) {
		EmailInfo mail = new EmailInfo();
		mail.setTo(user.getEmail());
		mail.setBody(body);
		mail.setSubject(subject);
		this.queue(mail);
	}

	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			EmailInfo email = list.remove(0);
			try {
				this.send(email);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}