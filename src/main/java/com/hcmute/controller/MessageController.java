package com.hcmute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hcmute.Entity.MessagesEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Models.MessageModel;
import com.hcmute.Service.MessageService;

@Controller
public class MessageController {

	private final MessageService messageService;
	Long user1 = (long) 3;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/firebase")
	public String showMessageFireBase(Model model, @RequestParam(name = "uid", required = false, defaultValue = "0") Long user2) {
		
		if (user2 == 0 ) {
	        // Xử lý khi uid không được truyền, ví dụ chuyển hướng hoặc trả về một giá trị mặc định
	        return "redirect:/list_Conversation";
	    }
		List<MessagesEntity> messages = messageService.getAllMessagesFromFirebase(user1, user2);
		String conversationId = messageService.generateConversationId(user1, user2);
		model.addAttribute("conversationId", conversationId);
		//model.addAttribute("messages", messages);
		return "chat";
	}

	@PostMapping("/send")
	public String sendMessage(@ModelAttribute("message") MessageModel message, @RequestParam(name = "uid") Long user2) {
		message.setSender(user1);
		messageService.sendMessage(message, user1, user2);
		return "redirect:/firebase?uid="+user2;
	}


	@GetMapping("/list_Conversation")
   public String showAllConversation(Model model) {
		List<Long> listUserID = messageService.findAllUserIdsInConversations(user1);
		List<UserInfoEntity> list = messageService.listInfoReceiverByIdAccount(listUserID);
		model.addAttribute("list",list);
		return "conversation";
	}

}
