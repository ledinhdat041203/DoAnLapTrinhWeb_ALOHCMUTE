  package vn.hcmute.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.ICommentService;
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

	@GetMapping("/listpost")
	public String post(Model model, HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity userInfo = userInfoService.findById(userid).get();
		List<PostModel> posts = postService.getPostsByGroupId(1, 0, 2, userid);
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("list", posts);
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
		Date currentSQLDate = new Date(System.currentTimeMillis());
		post.setPostDate(currentSQLDate);

		postService.save(post);
		return "redirect:/listpost";
	}

	@GetMapping("/listpost/group/{page}")
    public String getPostsByGroupId(
            @PathVariable int page,
            @RequestParam(defaultValue = "2") int size,
            Model model,
            HttpSession session)   {
		Long userid = (long) session.getAttribute("userInfoID");
		List<PostModel> posts = postService.getPostsByGroupId(1, page, size, userid);
		System.out.println(page);
		model.addAttribute("list", posts);
		return "listpost :: #listpost";

	}

	@GetMapping("/post/update/{postID}")
	public String getUpdatePost(Model post, @PathVariable long postID) {
		PostEntity existingPost = postService.findById(postID).get();
		post.addAttribute("post", existingPost);
		return "updatepost";
	}

	@PostMapping("/post/update/{postId}")
	public String updatePost(@PathVariable Long postId, @ModelAttribute("post") PostEntity updatedPost) {
		// Lấy ra post cần cập nhật
		PostEntity existingPost = postService.findById(postId).get();

		// Cập nhật thông tin từ biểu mẫu chỉnh sửa
		existingPost.setContent(updatedPost.getContent());
		existingPost.setImage(updatedPost.getImage());

		postService.save(existingPost);

		return "redirect:/listpost";
	}

	@DeleteMapping("/post/{postId}")
	@Transactional
	public ResponseEntity<String> deletePost(@PathVariable long postId) {
		System.out.println(postId);
		if (postService.existsById(postId)) {
			commentService.deleteAllByPostId(postId);
			likeService.deleteAllByPostPostId(postId);
			postService.deleteById(postId);
			return ResponseEntity.ok("Bài viết đã được xóa thành công!");
		} else {
			System.out.println("");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết có ID: " + postId);
		}

	}

}
