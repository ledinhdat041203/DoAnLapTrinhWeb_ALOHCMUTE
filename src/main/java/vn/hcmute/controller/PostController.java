package vn.hcmute.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;


@Controller
public class PostController {
	
	GroupEntity group = new GroupEntity(); //hardcode
	@Autowired
	IPostService postService;
	
	@Autowired
	IUserInfoService userInfoService;
	
	@GetMapping("/listpost")
	public String post(Model model) {
		List<PostModel> posts = postService.getPostsByGroupId(1, 0, 2);
		
		model.addAttribute("list", posts);
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
		group.setGroupID(1);
		post.setGroupPost(group);
		post.setImage(request.getImageURL());
		post.setContent(request.getContent());
		       
        // Tạo đối tượng java.sql.Date từ thời gian hiện tại
        Date currentSQLDate = new Date(System.currentTimeMillis());
        post.setPostDate(currentSQLDate);
        
		postService.save(post);
		return "redirect:/listpost";	
	}
	
	@GetMapping("/listpost/group/{page}")
    public String getPostsByGroupId(
            @PathVariable int page,
            @RequestParam(defaultValue = "2") int size,
            Model model) {
		
		List<PostModel> posts = postService.getPostsByGroupId(1, page, size);
		System.out.println(page);
		model.addAttribute("list", posts);
        return "listpost :: #listpost";

    }
}
