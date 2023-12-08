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

	// Xuất danh sách những user mà current user đang theo dõi
	@GetMapping("/following-list")
	public String list(ModelMap model, HttpSession session) {
		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		List<FriendsEntity> listFollowing = friendsService.findByuser1userID(idUsercurrent);
		List<UserInfoEntity> listUser2 = new ArrayList<>();

		for (FriendsEntity following : listFollowing) {
			if (following.isStatus()) {
				UserInfoEntity user2 = following.getUser2();
				listUser2.add(user2);
			}
		}

		model.addAttribute("listUser2", listUser2);
		return "listFollowing";
	}

	// Xuất danh sách những user đang theo dõi current user
	@GetMapping("/follower-list")
	public String listFollower(ModelMap model, HttpSession session) {
		Long idUsercurrent = (long) session.getAttribute("userInfoID");
		List<FriendsEntity> listFollower = friendsService.findByuser2userID(idUsercurrent);

		// Danh sách những người đang follow user current
		List<UserInfoEntity> listUserFollower = new ArrayList<>();
		for (FriendsEntity follower : listFollower) {
			if (follower.isStatus()) {
				UserInfoEntity user1 = follower.getUser1();
				listUserFollower.add(user1);
			}
		}

		// Tìm danh sách những user follow qua lại vs user current
		List<UserInfoEntity> listFriend = new ArrayList<>();
		Iterator<UserInfoEntity> iterator = listUserFollower.iterator();

		while (iterator.hasNext()) {
			UserInfoEntity follower = iterator.next();
			Long user2Id = follower.getUserID();
			FriendsEntity friend = friendsService.findByUser1IDAndUser2ID(idUsercurrent, user2Id);

			if (friend != null && friend.isStatus()) {
				listFriend.add(follower);
				iterator.remove(); // Sử dụng Iterator để xoá phần tử an toàn
			}
		}

		model.addAttribute("listUserFollower", listUserFollower);
		model.addAttribute("listFriend", listFriend);
		return "listFollower";
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
			String link = "friends/follower-list";
			String content = user1.getFullName() + " đã follow bạn";
			notificationService.createNotification(user2, link, content, user1.getAvata());
		}

		return ResponseEntity.ok("yes");
	}

	// Đề xuất theo dõi
	@GetMapping("/suggest-follow-up")
	public String suggestFollowUp(ModelMap model, HttpSession session) {
		Random random = new Random();
		List<UserInfoEntity> userList = new ArrayList<>();
		long sizeList = 20; // cần kiểm tra hệ thống có đủ bạn cho usercurrent hay không

		// Kiểm tra xem hệ thống có còn đủ 20 user để đề xuất hay không
		long numberUser = userService.count();
		long numberFollowing = 0;
		Long idUsercurrent = (long) session.getAttribute("userInfoID");
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

		model.addAttribute("userList", userList);
		return "suggestFollowUp";
	}
}
