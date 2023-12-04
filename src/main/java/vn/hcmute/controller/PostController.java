package vn.hcmute.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vn.hcmute.Entity.PostEntity;
import vn.hcmute.Entity.UserInfoEntity;
import vn.hcmute.Models.PostModel;
import vn.hcmute.Models.PostRequestModel;
import vn.hcmute.Service.IPostService;

@Controller
public class PostController {
	@Autowired
	IPostService postService;
	
	//UserInfoEntity user = new UserInfoEntity(3,"Lê Đình Đạt");
	UserInfoEntity user = new UserInfoEntity(2,"Trần Văn Đại");
	
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
		post.setGroupID(1);
		post.setImage(request.getImageURL());
		post.setContent(request.getContent());
		       
        // Tạo đối tượng java.sql.Date từ thời gian hiện tại
        Date currentSQLDate = new Date(System.currentTimeMillis());
        post.setPostDate(currentSQLDate);
        
		postService.save(post);
		return "listpost";	
	}
}

