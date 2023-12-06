package com.hcmute.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hcmute.Entity.CommentEntity;
import com.hcmute.Entity.GroupEntity;
import com.hcmute.Entity.PostEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Models.PostModel;
import com.hcmute.Models.PostRequestModel;
import com.hcmute.Service.ICommentService;
import com.hcmute.Service.ILikeService;
import com.hcmute.Service.IPostService;
import com.hcmute.Service.IUserInfoService;
import com.hcmute.Service.PostService;

@Controller
public class PostController {
	@Autowired
	IPostService postService;
	@Autowired
	ILikeService likeService;
	@Autowired
	IPostService postservice;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ICommentService commentService;
	

	
	UserInfoEntity user = new UserInfoEntity(3,"Lê Đình Đạt");
	GroupEntity group = new GroupEntity();
	//UserInfoEntity user = new UserInfoEntity(2,"Trần Văn Đại");
	
	@GetMapping("/listpost")
	public String post(Model model) {
		java.util.List<PostModel> list = postService.findAll();
		
		model.addAttribute("list", list);
		return "listpost";
	}
	
	@GetMapping("/post")
	public String post(ModelMap model) {
		model.addAttribute("post", new PostEntity());
		return "post";
	}
	
	@PostMapping(value = "/post", produces = "text/html;charset=UTF-8")
	public String savePost(@RequestBody PostRequestModel request) {
		PostEntity post = new PostEntity();
		post.setUser(user);
		group.setGroupID(1);
		post.setGroupPost(group);
		post.setImage(request.getImageURL());
		post.setContent(request.getContent());
		       
        // Tạo đối tượng java.sql.Date từ thời gian hiện tại
        Date currentSQLDate = new Date(System.currentTimeMillis());
        post.setPostDate(currentSQLDate);
        
		postService.save(post);
		return "listpost";	
	}
	
	@PostMapping(value = "/listpost/comment/create/{postId}")
	public ResponseEntity<String> createComment(
	        @PathVariable long postId,
	        @RequestBody  Map<String, String> commentData) {
	    String content = commentData.get("content");
	    LocalDate currentDate = LocalDate.now();
	    java.sql.Date utilDate = java.sql.Date.valueOf(currentDate);

	    // Tạo mới một CommentEntity và thiết lập giá trị
	    CommentEntity newComment = new CommentEntity(
	            0,  // Hoặc giá trị mặc định tùy thuộc vào cách bạn xử lý ID trong cơ sở dữ liệu
	            content,
	            postId,  // Truyền postid từ @PathVariable
	            1,    // UserInfoEntity có thể cần được thiết lập tùy thuộc vào logic của bạn
	            utilDate  // Giữ nguyên giá trị null cho commentDate, có thể cần được thiết lập tùy thuộc vào logic của bạn
	    );

	    // Lưu CommentEntity mới tạo
	    commentService.save(newComment);

	    return new ResponseEntity<>(content, HttpStatus.OK);
	}
}
