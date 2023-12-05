package vn.iostar.controller;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;
import vn.iostar.entities.PostEntity;
import vn.iostar.entities.UserInfoEntity;
import vn.iostar.model.PostModel;
import vn.iostar.service.IPostService;
import vn.iostar.service.IUserInfoService;



@Controller
public class PostController {
	@Autowired
	IPostService postService;
	
	@Autowired
	IUserInfoService userInfoService;
	
	@GetMapping("/listpost")
	public String post(Model model) {
		java.util.List<PostEntity> list = postService.findAll();
		
		model.addAttribute("list", list);
		return "listpost";
	}
	
	@GetMapping("/post")
	public String post(ModelMap model) {
		model.addAttribute("post", new PostEntity());
		return "post";
	}
	
	@PostMapping(value = "/post", produces = "text/html;charset=UTF-8")
	public String savePost(@RequestBody PostModel request, HttpSession session) {
		
		//lay tu session
		Long userID = (Long) session.getAttribute("userInfoID");
		UserInfoEntity user = new UserInfoEntity();
		//UserInfoEntity user = userInfoService.findByUserIDEquals(userID).get();

		Optional<UserInfoEntity> userOptional =
		userInfoService.findByUserIDEquals(userID); 
		if (userOptional.isPresent()) {
			user = userOptional.get(); 
		} else {
			System.out.println("Loi roi"); 
		}

		
		PostEntity post = new PostEntity();
		post.setUser(user);
		post.setGroupID(1); //hardcode
		post.setImage(request.getImageURL());
		post.setContent(request.getContent());
		       
        // Tạo đối tượng java.sql.Date từ thời gian hiện tại
        Date currentSQLDate = new Date(System.currentTimeMillis());
        post.setPostDate(currentSQLDate);
        
		postService.save(post);
		return "redirect:/listpost";	
	}
}
