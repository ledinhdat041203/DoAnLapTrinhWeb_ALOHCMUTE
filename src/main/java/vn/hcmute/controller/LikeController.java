package vn.hcmute.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.service.ILikeService;
import vn.hcmute.service.INotificationService;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.PostService;

@Controller
public class LikeController {

	@Autowired
	ILikeService likeService;
	@Autowired
	IPostService postservice;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	INotificationService notificationService;

	@Autowired
	PostService postService;

	@PostMapping("/like/{postId}")
	public ResponseEntity<Long> likePost(@PathVariable long postId, HttpSession session) {
		
		Long userid = (long) session.getAttribute("userInfoID");
		PostEntity post = postservice.findById(postId).get();
		UserInfoEntity user = userInfoService.findById(userid).get();
		LikeEntity LikeEntity = likeService.findLikeByPostAndUser(post, userid);
		
		if (LikeEntity != null) {
			if (LikeEntity.isStatus())
				LikeEntity.setStatus(false);
			else
				LikeEntity.setStatus(true);
			likeService.save(LikeEntity);
		} else {
			LikeEntity = new LikeEntity();
			LikeEntity.setLikeDate(new Date(System.currentTimeMillis()));
			LikeEntity.setStatus(true);
			LikeEntity.setPost(post);
			LikeEntity.setUserLike(user);
			likeService.save(LikeEntity);
		}

		// Xử lí thông báo
		if (LikeEntity.isStatus()) {
			// Chưa xong khúc này (chưa có link)
			String link = "Chưa có gì cả";
			String content = user.getFullName() + " đã thả tim bài viết của bạn";
			UserInfoEntity user2 = post.getUser();
			notificationService.createNotification(user2, link, content);
		}
		
		List<LikeEntity> listLike = postservice.findById(postId).get().getListLikes();
		Long likeCount = (long) 0;
		for (LikeEntity like : listLike) {
			System.out.println(like.isStatus());
			if (like.isStatus() == true)
				likeCount++;
		}
		System.out.println(likeCount);
		
		return ResponseEntity.ok(likeCount);
	}

}
