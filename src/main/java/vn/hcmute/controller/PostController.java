package vn.hcmute.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.ICommentService;
import vn.hcmute.service.IFriendsService;
import vn.hcmute.service.ILikeService;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;

@Controller
public class PostController {

	GroupEntity group = new GroupEntity();
	@Autowired
	IPostService postService;
	@Autowired
	ICommentService commentService;
	@Autowired
	ILikeService likeService;

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	FriendsController friendController;
	@Autowired
	IFriendsService friendsService;
	
	
	
	@GetMapping("/listpost")
	public String postFriend(Model model, HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity userInfo = userInfoService.findById(userid).get();
		List<FriendsEntity> listFollow = friendsService.findByuser1userID(userid);
		List<UserInfoEntity> listFriend = new ArrayList<>();
		for (FriendsEntity follow : listFollow) {
			if (follow.isStatus()) {
				UserInfoEntity user2 = follow.getUser2();
				listFriend.add(user2);
			}
		}
		List<PostModel> listPost = new ArrayList<>();
		for (UserInfoEntity user : listFriend) {
			List<PostModel> postFriend = postService.findByUserUserID(user.getUserID()); 
			for (PostModel post : postFriend) {
				listPost.add(post);
			}
		}
		
		List<UserInfoEntity> listSuggest = friendController.suggest(userid, 5);
		
		model.addAttribute("listSuggest", listSuggest);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("list", listPost);
		return "listPostFriend";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/explore")
	public String post(Model model, HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity userInfo = userInfoService.findById(userid).get();
		List<PostModel> posts = postService.getPostsByGroupId(1, 0, 2, userid);
		List<UserInfoEntity> listSuggest = friendController.suggest(userid, 5);
		
		model.addAttribute("listSuggest", listSuggest);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("list", posts);
		model.addAttribute("demo", posts.get(1));
		return "listpost";
	}
	
	
	
	
	@GetMapping("/post")
	public String post(ModelMap model) {
		model.addAttribute("post", new PostEntity());
		return "post";
	}

	@PostMapping(value = "/post/{groupID}", produces = "text/html;charset=UTF-8")
	public String savePost(@PathVariable long groupID, @RequestBody PostModel request, HttpSession session) {
		// lay tu session
		Long userID = (Long) session.getAttribute("userInfoID");
		UserInfoEntity user = new UserInfoEntity();
		// UserInfoEntity user = userInfoService.findByUserIDEquals(userID).get();

		Optional<UserInfoEntity> userOptional = userInfoService.findByUserIDEquals(userID);
		if (userOptional.isPresent()) {
			user = userOptional.get();
		} else {
			System.out.println("Loi roi");
		}
		PostEntity post = new PostEntity();
		post.setUser(user);
		group.setGroupID(groupID);
		post.setGroupPost(group);

		post.setImage(request.getImageURL());
		post.setContent(request.getContent());

		// Tạo đối tượng java.sql.Date từ thời gian hiện tại
		long currentSQLDate = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentSQLDate);
		post.setPostDate(currentTimestamp);

		postService.save(post);
		return "redirect:/listpost";
	}

	@GetMapping("/listpost/group/{page}")
    public String getPostsByGroupId(
            @PathVariable int page,
            @RequestParam(defaultValue = "2") int size,
            Model model,
            HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		List<PostModel> posts = postService.getPostsByGroupId(1, page, size, userid);
		System.out.println(page);
		model.addAttribute("list", posts);
		//model.addAttribute("fragment", "post");  // Sửa dòng này để truyền danh sách bài viết vào fragment
	    return "listpost :: #listpost";

	}
	@GetMapping("/post/detail/{postId}")
	public String postDetail(@PathVariable long postId,ModelMap model)
	{
		PostEntity post = postService.findById(postId).get();
		PostModel postModel = postService.converEntityToModel(post, post.getUser().getUserID());
		
		
		model.addAttribute("post", postModel);
		return "commentTemplate";
	}
	
	@GetMapping("/post/update/{postID}")
	public String getUpdatePost(Model post, @PathVariable long postID) {
		PostEntity existingPost = postService.findById(postID).get();
		post.addAttribute("post", existingPost);
		return "updatepost";
	}
	
	@GetMapping("/post/post-info/{postID}")
	public ResponseEntity<PostModel> findPostInfoByID(@PathVariable long postID, HttpSession session)
	{
		long userID = (long)session.getAttribute("userInfoID");
		PostEntity postEntity = postService.findById(postID).get();
		PostModel postMoel = postService.converEntityToModel(postEntity, userID);
		return ResponseEntity.ok(postMoel);
	}
	
	@PostMapping("/post/update/{postId}")
	public String updatePost(@PathVariable Long postId, @RequestBody PostModel request) {
		System.out.println("----------------------------------------------" +request.getContent());
		PostEntity post = postService.findById(postId).get();
		post.setImage(request.getImageURL());
		post.setContent(request.getContent());
		// Tạo đối tượng java.sql.Date từ thời gian hiện tại
		long currentSQLDate = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentSQLDate);
		post.setPostDate(currentTimestamp);

		postService.save(post);

		return "redirect:/listpost";
	}

	@GetMapping("/deletepost/{postId}")
	@Transactional
	public String deletePost(@PathVariable long postId) {
		System.out.println(postId);
		if (postService.existsById(postId)) {
			commentService.deleteAllByPostId(postId);
			likeService.deleteAllByPostPostId(postId);
			postService.deleteById(postId);
		}
		
		return "redirect:/listpost";

	}

}