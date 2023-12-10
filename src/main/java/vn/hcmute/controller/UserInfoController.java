package vn.hcmute.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.model.UserInfoModel;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;

@Controller
@RequestMapping("/profile")
public class UserInfoController {
	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IPostService postService;

	@GetMapping("/{userID}")
	public String list(ModelMap model, HttpSession session, ModelMap post, Model listpost, @PathVariable long userID) {

		if (userID == 0) {
			// Xử lý trường hợp userID là null
			return "redirect:/login"; // hoặc chuyển hướng đến trang đăng nhập khác
		}
		List<PostModel> posts = postService.findByUserUserID(userID);
		Optional<UserInfoEntity> userOptional = userInfoService.findById(userID);
		if (userOptional.isEmpty()) {
			// Xử lý trường hợp userInfo không tồn tại
			return "redirect:/profile";
		}
		UserInfoEntity user = userOptional.get();
		List<FriendsEntity> listfollowing = user.getListFriend1();

		List<FriendsEntity> listfollower = user.getListFriend2();
		long countFollowing = 0;
		long countFollower = 0;
		for (FriendsEntity friend : listfollowing) {
			if (friend.isStatus()) {
				countFollowing++;
			}
		}
		for (FriendsEntity friend : listfollower) {
			if (friend.isStatus()) {
				countFollower++;
			}
		}
		UserInfoModel userModel = userInfoService.convertToUserInfoModel(user);

		userModel.setCountFollower(countFollower);
		userModel.setCountFollowing(countFollowing);

		userModel.setCountPost((long) posts.size());

		model.addAttribute("info", userModel);
		post.addAttribute("post", new PostEntity());
		listpost.addAttribute("list", posts);

		return "profile";
	}

	@GetMapping("/update")
	public String update(Model model, HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		Optional<UserInfoEntity> userInfoOptional = userInfoService.findById(userid);
		UserInfoEntity userInfo = userInfoOptional.get();
		model.addAttribute("userInfo", userInfo);
		return "update";
	}

	@PostMapping("/saveUpdate")
	public String saveUserInfoUpdate(@ModelAttribute("userInfo") UserInfoEntity userInfo, HttpSession session) {
		long curren_user = (long) session.getAttribute("userInfoID");
		if (curren_user == userInfo.getUserID()) {
			Optional<UserInfoEntity> existingUser = userInfoService.findById(userInfo.getUserID());
			if (existingUser.isPresent()) {
				userInfoService.save(userInfo);
			}
		}
		return "redirect:/profile/"+userInfo.getUserID();
	}
}
