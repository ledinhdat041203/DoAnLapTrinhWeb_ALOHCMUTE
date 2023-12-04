package vn.hcmute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import vn.hcmute.service.MessageService;



@Controller
public class MessageController {

	private final MessageService messageService;
	

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/firebase")
	public String showMessageFireBase(Model model, @RequestParam(name = "uid", required = false, defaultValue = "0") Long user2, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		if (user2 == 0 ) {
	        // Xử lý khi uid không được truyền, ví dụ chuyển hướng hoặc trả về một giá trị mặc định
	        return "redirect:/list_Conversation";
	    }
		List<MessagesEntity> messages = messageService.getAllMessagesFromFirebase(user1, user2);
		String conversationId = messageService.generateConversationId(user1, user2);
		model.addAttribute("conversationId", conversationId);
		model.addAttribute("messages", messages);
		return "chat";
	}

	@PostMapping("/send")
	public String sendMessage(@ModelAttribute("message") MessageModel message, @RequestParam(name = "uid") Long user2, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		message.setSender(user1);
		messageService.sendMessage(message, user1, user2);
		return "redirect:/firebase?uid="+user2;
	}

	@GetMapping("/list_Conversation")
   public String showAllConversation(Model model, HttpSession session) {
		Long user1 = (long) session.getAttribute("userInfoID");
		List<Long> listUserID = messageService.findAllUserIdsInConversations(user1);
		List<UserInfoEntity> list = messageService.listInfoReceiverByIdAccount(listUserID);
		model.addAttribute("list",list);
		return "conversation";
	}

}
