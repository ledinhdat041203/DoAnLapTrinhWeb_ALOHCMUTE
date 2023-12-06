package com.hcmute.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hcmute.Entity.LikeEntity;
import com.hcmute.Entity.PostEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Service.ILikeService;
import com.hcmute.Service.IPostService;
import com.hcmute.Service.IUserInfoService;
import com.hcmute.Service.PostService;

@Controller
public class LikeController {

	@Autowired
	ILikeService likeService;
	@Autowired
	IPostService postservice;
	@Autowired
	IUserInfoService userInfoService;
	
	@Autowired
	PostService postService;


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

}
