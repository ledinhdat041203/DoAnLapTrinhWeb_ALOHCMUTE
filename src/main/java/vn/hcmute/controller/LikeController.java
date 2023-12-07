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
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;



@Controller
public class LikeController {

	@Autowired
	ILikeService likeService;
	@Autowired
	IPostService postservice;
	@Autowired
	IUserInfoService userInfoService;
	
	@Autowired
	IPostService postService;


	 @PostMapping("/like/{postId}") 
	 public ResponseEntity<Long> likePost(@PathVariable long postId, HttpSession session) { 
		 System.out.print("Post id nha: "+ postId); 
		 Long userid = (long) session.getAttribute("userInfoID");
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
	 
}
