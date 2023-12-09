package vn.hcmute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.NotificationEntity;
import vn.hcmute.service.INotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	INotificationService notificationService;
	
	@GetMapping("/notification-list")
	public String listNotification(ModelMap model, HttpSession session) {
		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		List<NotificationEntity> listNotification = notificationService.findByUserUserID(idUsercurrent);
		int count = 0;
		for (NotificationEntity notice : listNotification) {
			if (!notice.isStatus())
				count++;
		}
		model.addAttribute("unreadNotify", count);                   
		model.addAttribute("listNotification", listNotification);
		return "listNotification";
		
	}
	
	
}
