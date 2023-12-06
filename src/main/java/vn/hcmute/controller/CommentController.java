package vn.hcmute.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hcmute.*;
import vn.hcmute.Entity.CommentEntity;
import vn.hcmute.Entity.LikeEntity;
import vn.hcmute.Entity.PostEntity;
import vn.hcmute.Entity.UserInfoEntity;
import vn.hcmute.Models.CommentRequestModel;
import vn.hcmute.Service.ICommentService;
import vn.hcmute.Service.ILikeService;
import vn.hcmute.Service.IPostService;
import vn.hcmute.Service.IUserInfoService;
import vn.hcmute.Service.PostService;

@Controller
public class CommentController {

	@Autowired
	ILikeService likeService;
	@Autowired
	IPostService postservice;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ICommentService commentService;
	
	@Autowired
	PostService postService;


	@GetMapping("/comment/{postId}")
	public ResponseEntity<List<CommentEntity>> getComments(@PathVariable long postId) {
	    System.out.print("Post id nha: " + postId);

	    // Debug để xác nhận giá trị của postId
	    List<CommentEntity> listComment = commentService.findCommentByPost(postId);
	    System.out.print(listComment);

	    return new ResponseEntity<>(listComment, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create/{postId}", consumes = "application/json")
	public ResponseEntity<String> createComment(
	        @PathVariable long postId,
	        @RequestBody Map<String, String> commentData) {
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
	
	 @DeleteMapping(value = "/comment/delete/{postId}/{commentId}")
	    // public ResponseEntity<String> deleteComment(@PathVariable Long commentId ,@RequestBody Map<Long, Long> commentData) 
	 public ResponseEntity<String> deleteComment(@PathVariable Long postId,@PathVariable Long commentId)
	 {
		 Long userid = (long) 1;
		 commentService.deleteComment(commentId, userid,postId);
		 return new ResponseEntity<>("thanh cong", HttpStatus.OK);
	         
	 }
	 
	 @PutMapping(value = "/comment/update/{postId}", consumes = "application/json")
		public ResponseEntity<String> updateComment(
		        @PathVariable long postId,
		        @RequestBody CommentRequestModel commentData) {
		    String content = commentData.getContent();
		    long commentId = commentData.getCommentId();
		    LocalDate currentDate = LocalDate.now();
		    java.sql.Date utilDate = java.sql.Date.valueOf(currentDate);
		    Long userid = (long) 1;
		    

		    // Lưu CommentEntity mới tạo
		    commentService.updateComment(commentId, userid, postId, content);

		    return new ResponseEntity<>(content, HttpStatus.OK);
		}

	



   

}
