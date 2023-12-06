package vn.hcmute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.service.INotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	INotificationService notificationService;


}
