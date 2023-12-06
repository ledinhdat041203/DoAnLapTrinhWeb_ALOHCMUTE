package com.hcmute.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcmute.Entity.GroupEntity;
import com.hcmute.Entity.PostEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Models.PostModel;
import com.hcmute.Models.PostRequestModel;
import com.hcmute.Service.IPostService;

@Controller
public class PostController {
	@Autowired
	IPostService postService;
	
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
}
