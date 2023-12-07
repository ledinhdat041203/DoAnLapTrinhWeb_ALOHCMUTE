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

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.CommentEntity;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.model.CommentRequestModel;
import vn.hcmute.service.ICommentService;
import vn.hcmute.service.ILikeService;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.PostService;

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

        // Lấy danh sách bình luận
        List<CommentEntity> listComment = commentService.findCommentByPost(postId);
        System.out.print(listComment);

        // Đếm số lượng bình luận
        Long commentCount = commentService.countCommentsByPostId(postId);

        // Gán số lượng bình luận vào danh sách và trả về
        for (CommentEntity comment : listComment) {
            comment.setCommentCount(commentCount);
        }
        System.out.println("cmt =" + commentCount);
        return new ResponseEntity<>(listComment, HttpStatus.OK);
    }


	@PostMapping(value = "/create/{postId}", consumes = "application/json")
	public ResponseEntity<String> createComment(@PathVariable long postId, @RequestBody Map<String, String> commentData,
			HttpSession session) {
		String content = commentData.get("content");
		LocalDate currentDate = LocalDate.now();
		java.sql.Date utilDate = java.sql.Date.valueOf(currentDate);
		Long userid = (long) session.getAttribute("userInfoID");

		// Tạo mới một CommentEntity và thiết lập giá trị
		CommentEntity newComment = new CommentEntity(0, // Hoặc giá trị mặc định tùy thuộc vào cách bạn xử lý ID trong
														// cơ sở dữ liệu
				content, postId, // Truyền postid từ @PathVariable
				userid, // UserInfoEntity có thể cần được thiết lập tùy thuộc vào logic của bạn
				utilDate // Giữ nguyên giá trị null cho commentDate, có thể cần được thiết lập tùy thuộc
							// vào logic của bạn
		);

		// Lưu CommentEntity mới tạo
		commentService.save(newComment);
		Long commentCount = commentService.countCommentsByPostId(postId);
		String commentCountString1 = String.valueOf(commentCount);

		return new ResponseEntity<>(commentCountString1, HttpStatus.OK);
	}

	@DeleteMapping(value = "/comment/delete/{postId}/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
			HttpSession session) {
		Long userId = (long) session.getAttribute("userInfoID");
		boolean isDeleted = commentService.deleteComment(commentId, userId, postId);
		Long commentCount = commentService.countCommentsByPostId(postId);
		String commentCountString1 = String.valueOf(commentCount);
		if (isDeleted) {
			return new ResponseEntity<>(commentCountString1, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(commentCountString1, HttpStatus.BAD_REQUEST);
		}
	}


	


	@PutMapping(value = "/comment/update/{postId}", consumes = "application/json")
	public ResponseEntity<String> updateComment(@PathVariable long postId, @RequestBody CommentRequestModel commentData,
			HttpSession session) {
		String content = commentData.getContent();
		long commentId = commentData.getCommentId();
		LocalDate currentDate = LocalDate.now();
		java.sql.Date utilDate = java.sql.Date.valueOf(currentDate);
		Long userid = (long) session.getAttribute("userInfoID");

		boolean isUpdate = commentService.updateComment(commentId, userid, postId, content);
		if (isUpdate) {
			return new ResponseEntity<>("thanh cong", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("thatbai", HttpStatus.BAD_REQUEST);
		}

	}
	

}
