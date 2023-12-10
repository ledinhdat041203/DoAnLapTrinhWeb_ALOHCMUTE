package vn.hcmute.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.service.IFriendsService;
import vn.hcmute.service.INotificationService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.IUserService;

@Controller
@RequestMapping("/friends")
public class FriendsController {
	@Autowired
	IFriendsService friendsService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IUserService userService;
	@Autowired
	INotificationService notificationService;

	// Xuất danh sách follower và following
	@GetMapping({"/following-list/{userID}", "/follower-list/{userID}"})
	public String list(ModelMap model, HttpSession session, @PathVariable long userID, HttpServletRequest req) {
		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		String url = req.getRequestURL().toString();
		List<FriendsEntity> listFollow;
		List<UserInfoEntity> listUser = new ArrayList<>();
		UserInfoEntity user = userInfoService.findById(idUsercurrent).get();
		UserInfoEntity userSearch = userInfoService.findById(userID).get();
		
		boolean checkMe = false;
		
		if (url.contains("following")) {
			model.addAttribute("pageTitle", userSearch.getFullName() + " are following");
			listFollow = friendsService.findByuser1userID(userID);
			for (FriendsEntity follow : listFollow) {
				if (follow.isStatus() && (follow.getUser2().getUserID() != idUsercurrent)) {
					UserInfoEntity user2 = follow.getUser2();
					listUser.add(user2);
				}
				if (follow.getUser2().getUserID() == idUsercurrent) {checkMe = true;}
			}
		} else {
			model.addAttribute("pageTitle", "Following " + userSearch.getFullName());
			listFollow = friendsService.findByuser2userID(userID);
			for (FriendsEntity follow : listFollow) {
				if (follow.isStatus() && (follow.getUser1().getUserID() != idUsercurrent)) {
					UserInfoEntity user1 = follow.getUser1();
					listUser.add(user1);
				}
				if (follow.getUser1().getUserID() == idUsercurrent) {checkMe = true;}
			}
		}
		
		List<UserInfoEntity> listFriend = new ArrayList<>();
		Iterator<UserInfoEntity> iterator = listUser.iterator();

		while (iterator.hasNext()) {
			UserInfoEntity follower = iterator.next();
			Long user2Id = follower.getUserID();
			FriendsEntity friend = friendsService.findByUser1IDAndUser2ID(idUsercurrent, user2Id);

			if (friend != null && friend.isStatus()) {
				listFriend.add(follower);
				iterator.remove(); // Sử dụng Iterator để xoá phần tử an toàn
			}
		}
		
		model.addAttribute("userInfoCurrent", user);
		model.addAttribute("checkMe", checkMe);
		model.addAttribute("listFriend", listFriend);
		model.addAttribute("listUser", listUser);
		return "listFollow";
	}

	// Xử lý theo dõi
	@GetMapping("/follow/{user2Id}")
	public ResponseEntity<String> followUsers(@PathVariable long user2Id, HttpSession session) {

		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		FriendsEntity friend = friendsService.findByUser1IDAndUser2ID(idUsercurrent, user2Id);

		if (friend != null) {
			if (friend.isStatus()) {
				friend.setStatus(false);
			} else {
				friend.setStatus(true);
			}

			friendsService.save(friend);
		} else {
			UserInfoEntity user1 = userInfoService.findById(idUsercurrent).get();
			UserInfoEntity user2 = userInfoService.findById(user2Id).get();

			friendsService.createFriendsByUser1AndUser2(user1, user2, true);

			// Xử lí thông báo
			String link = "/profile/"+user1;
			String content = user1.getFullName() + " đã follow bạn";
			notificationService.createNotification(user2, link, content, user1.getAvata());
		}

		return ResponseEntity.ok("yes");
	}

	// Đề xuất theo dõi
	@GetMapping("/suggest-follow-up")
	public String suggestFollowUp(ModelMap model, HttpSession session) {

		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		List<UserInfoEntity> userList = suggest(idUsercurrent, 20);
		model.addAttribute("userList", userList);
		return "suggestFollowUp";
	}

	public List<UserInfoEntity> suggest(long idUsercurrent, long sizeList) {
		Random random = new Random();
		List<UserInfoEntity> userList = new ArrayList<>();

		// Kiểm tra xem hệ thống có còn đủ 20 user để đề xuất hay không
		long numberUser = userService.count();
		long numberFollowing = 0;

		List<FriendsEntity> listFollowing = friendsService.findByuser1userID(idUsercurrent);
		for (FriendsEntity following : listFollowing) {
			if (following.isStatus())
				numberFollowing += 1;
		}
		if ((numberUser - numberFollowing - 1) < sizeList)
			sizeList = (numberUser - numberFollowing - 1);

		List<Long> listuserid = new ArrayList<>();
		for (long i = 0; i < sizeList;) {
			long randomId = random.nextLong(100) + 1;
			if (randomId != idUsercurrent) {
				FriendsEntity friend = friendsService.findByUser1IDAndUser2ID(idUsercurrent, randomId);
				boolean shouldAdd = false;

				if (friend == null) {
					shouldAdd = userInfoService.findById(randomId).isPresent() && !listuserid.contains(randomId);
				} else {
					shouldAdd = !friend.isStatus() && !listuserid.contains(randomId);
				}

				if (shouldAdd) {
					listuserid.add(randomId);
					i++;
				}
			}
		}

		for (long userid : listuserid) {
			UserInfoEntity user = userInfoService.findById(userid).get();
			userList.add(user);
		}
		return userList;
	}

}