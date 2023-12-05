package vn.hcmute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;

@Controller
public class UserInfoController {
	@Autowired
	IUserInfoService userInfoService;

	@Autowired
	IPostService postService;

	@GetMapping("/profile")
	public String list(ModelMap model, HttpSession session, ModelMap post, Model listpost) {
		// lay tu session
		long userID = (Long) session.getAttribute("userInfoID");
		UserInfoEntity user = userInfoService.findById((Long) userID).get();
		model.addAttribute("info", user);

		post.addAttribute("post", new PostEntity());

		List<PostModel> posts = postService.findByUserUserID(userID, 0, 2);
		listpost.addAttribute("list", posts);

		// Long userId = 3L; // Thay đổi userId theo logic của bạn
		// List<PostModel> userPosts = postService.findByUserUserID(userId);
		// postModelMap.addAttribute("listPosts", userPosts);

		return "profile";
	}

	@GetMapping("/update/{userid}")
	public String update(@PathVariable Long userid, Model model) {
		UserInfoEntity userInfo = userInfoService.findById(userid).get();
		if (userInfo != null) {
			model.addAttribute("userInfo", userInfo);
			return "update";
		} else {
			// Xử lý trường hợp userInfo không tồn tại
			return "redirect:/info/list";
		}
	}

	@PostMapping("/saveUpdate")
	public String saveUserInfoUpdate(@ModelAttribute("userInfo") UserInfoEntity userInfo) {
		if (userInfo != null) {
			userInfoService.save(userInfo);
			return "redirect:/info/list";
		} else {
			// Xử lý trường hợp userInfo là null
			return "redirect:/info/list";
		}
	}
}
