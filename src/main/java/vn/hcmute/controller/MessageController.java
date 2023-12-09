package vn.hcmute.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.MessagesEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.MessageModel;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.MessageService;


@Controller
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private IUserInfoService userInfoService;

	@GetMapping("/firebase")
	public String showMessageFireBase(Model model,
			@RequestParam(name = "uid", required = false, defaultValue = "-1") Long user2, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");

		if (user2 == -1) {
			// Xử lý khi uid không được truyền, ví dụ chuyển hướng hoặc trả về một giá trị
			// mặc định
			return "conversation";
		}
		List<MessagesEntity> messages = messageService.getAllMessagesFromFirebase(user1, user2);
		String conversationId = messageService.generateConversationId(user1, user2);
		// System.out.println(conversationId);
		UserInfoEntity userInfo = userInfoService.findByUserIDEquals(user2).get();
		// System.out.println(userInfo.getPhoneNumber());
		model.addAttribute("conversationId", conversationId);
		model.addAttribute("messages", messages);
		model.addAttribute("recipientInfo", userInfo);
		return "chat";
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendMessage(@ModelAttribute("message") MessageModel message,
			@RequestParam(name = "uid") Long user2, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		message.setSender(user1);
		messageService.sendMessage(message, user1, user2);
		return ResponseEntity.ok("ok");
	}

	@GetMapping("/list_Conversation")
	public String showAllConversation(Model model, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		List<Long> listUserID = messageService.findAllUserIdsInConversations(user1);
		List<UserInfoEntity> list = messageService.listInfoReceiverByIdAccount(listUserID);
		model.addAttribute("list", list);
		return "conversation";
	}

	@GetMapping("/delete-chat")
	public String deleteChat(Model model, @RequestParam(name = "uid") Long uid, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		messageService.deleteConversation(user1, uid);
		List<UserInfoEntity> listUser = new ArrayList<UserInfoEntity>();
		List<Long> listUserID = messageService.findAllUserIdsInConversations(user1);
		listUser = messageService.listInfoReceiverByIdAccount(listUserID);
		model.addAttribute("listUser", listUser);
		return "listUser";
	}

	@GetMapping("/findByName")
	public String findByName(Model model, @RequestParam(name = "nameSearch") String name,
			@RequestParam(name = "action") Integer action, HttpSession session) {
		List<UserInfoEntity> listUser = new ArrayList<UserInfoEntity>();
		if (action == 1) {
			if (name != "") {
				listUser = userInfoService.findByFullNameContaining(name);
			}
		} else {
			Long user1 = (long) session.getAttribute("userInfoID");
			List<Long> listUserID = messageService.findAllUserIdsInConversations(user1);
			listUser = messageService.listInfoReceiverByIdAccount(listUserID);
		}
		model.addAttribute("listUser", listUser);
		return "listUser";
	}
}
