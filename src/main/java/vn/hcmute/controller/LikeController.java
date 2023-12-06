package vn.hcmute.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

<<<<<<< HEAD:src/main/java/vn/hcmute/controller/LikeController.java
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.service.ILikeService;
import vn.hcmute.service.INotificationService;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.PostService;
=======
import com.hcmute.Entity.LikeEntity;
import com.hcmute.Entity.PostEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Service.ILikeService;
import com.hcmute.Service.IPostService;
import com.hcmute.Service.IUserInfoService;
import com.hcmute.Service.PostService;
>>>>>>> e05cefe3d877716d5bc39564ed2feeea6daae665:src/main/java/com/hcmute/controller/LikeController.java

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

<<<<<<< HEAD:src/main/java/vn/hcmute/controller/LikeController.java
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
=======
	 @PostMapping("/like/{postId}") 
	 public ResponseEntity<Long> likePost(@PathVariable long postId) { 
		 System.out.print("Post id nha: "+ postId); 
		 long userid = 3; // hardcode !!!!! 
		 PostEntity post = postservice.findById(postId).get(); 
		 UserInfoEntity user =userInfoService.findById(userid).get(); 
		 LikeEntity LikeEntity = likeService.findLikeByPostAndUser(post, userid); 
		 if (LikeEntity != null) {
			 System.out.println("Toi Day roi!!!!"); 
			 if (LikeEntity.isStatus())
				 LikeEntity.setStatus(false); 
			 else 
				 LikeEntity.setStatus(true);
			 likeService.save(LikeEntity); 
		 } else { 
			 System.out.println("Loi nayy!!");
			 LikeEntity like = new LikeEntity(); like.setLikeDate(new
			 Date(System.currentTimeMillis())); like.setStatus(true); like.setPost(post);
			 like.setUserLike(user);
			 likeService.save(like); 
		 }
	  
		 List<LikeEntity> listLike = postservice.findById(postId).get().getListLikes(); 
		 Long likeCount = (long) 0;
		 for (LikeEntity like : listLike) { 
			 System.out.println(like.isStatus()); 
			 if (like.isStatus() == true) likeCount++; 
		} 
		 System.out.println(likeCount);
		 return ResponseEntity.ok(likeCount); 
	}
	 

//	@PostMapping("/like/{postId}")
//	public String likePost(Model model,@PathVariable long postId) {
//		System.out.print("Post id nha: " + postId);
//		long userid = 3; // hardcode !!!!!
//		PostEntity post = postservice.findById(postId).get();
//		UserInfoEntity user = userInfoService.findById(userid).get();
//		LikeEntity LikeEntity = likeService.findLikeByPostAndUser(post, userid);
//		if (LikeEntity != null) {
//			System.out.println("Toi Day roi!!!!");
//			if (LikeEntity.isStatus())
//				LikeEntity.setStatus(false);
//			else
//				LikeEntity.setStatus(true);
//			likeService.save(LikeEntity);
//		} else {
//			System.out.println("Loi nayy!!");
//			LikeEntity like = new LikeEntity();
//			like.setLikeDate(new Date(System.currentTimeMillis()));
//			like.setStatus(true);
//			like.setPost(post);
//			like.setUserLike(user);
//
//			likeService.save(like);
//		}
//
//		List<LikeEntity> listLike = postservice.findById(postId).get().getListLikes();
//		Long likeCount = (long) 0;
//		for (LikeEntity like : listLike) {
//			System.out.println(like.isStatus());
//			if (like.isStatus() == true)
//				likeCount++;
//		}
//		java.util.List<PostModel> list = postService.findAll();
//
//		model.addAttribute("list", list);
//		System.out.println(likeCount);
//		return "listpost";
//	}
>>>>>>> e05cefe3d877716d5bc39564ed2feeea6daae665:src/main/java/com/hcmute/controller/LikeController.java

}
