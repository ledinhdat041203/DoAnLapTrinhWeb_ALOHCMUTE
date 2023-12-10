package vn.hcmute.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.hcmute.Responsitory.CommentRepository;

import vn.hcmute.entities.CommentEntity;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.CommentModel;



@Service
public class CommentService implements ICommentService{

	@Autowired
	CommentRepository commentRepo;
	
	@Override
	public CommentModel convertEntityToModel(CommentEntity comment) {
		CommentModel commentModel = new CommentModel();
		commentModel.setCommentID(comment.getCommentId());
		commentModel.setAvata(comment.getUserComment().getAvata());
		commentModel.setCommentDate(comment.getCommentDate());
		commentModel.setContent(comment.getContent());
		commentModel.setUserFullName(comment.getUserComment().getFullName());
		return commentModel;
	}
	
	@Override
	public <S extends CommentEntity> S save(S entity) {
        if (entity == null) {
            // Xử lý trường hợp entity là null nếu cần thiết
            throw new IllegalArgumentException("Entity cannot be null");
        }

        if (entity.getCommentId() == 0 || !commentRepo.existsById(entity.getCommentId())) {
            // Đây là trường hợp tạo mới
            CommentEntity newComment = new CommentEntity(
                0,  // Hoặc giá trị mặc định tùy thuộc vào cách bạn xử lý ID trong cơ sở dữ liệu
                entity.getContent(),
                entity.getPostCommnent() != null ? entity.getPostCommnent().getPostID() : 0,
                entity.getUserComment() != null ? entity.getUserComment().getUserID() : 0,
                entity.getCommentDate()

            );

            return commentRepo.save(entity);
        } else {
            // Đây là trường hợp cập nhật
            return commentRepo.save(entity);
        }
    }



    @Override
    public LikeEntity findLikeByPostAndUser(PostEntity post, long userID) {
        // Implement this method as needed
        return null;
    }

    @Override
    public List<CommentEntity> findCommentByPost(long postid) {
        return commentRepo.findByPostIDContaining(postid);
    }


  



	@Override
	public void createComment(long postId, long userId, String content, java.util.Date createdate) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void createComment(long postId, long userId, String content, Date createdate) {
		// TODO Auto-generated method stub
		
	}



	public boolean deleteComment(long commentId, long userId, long postId) {
	    if (commentRepo.countByUserIdAndPostIdAndCommentId(userId, postId, commentId) == 1) {
	        commentRepo.deleteById(commentId);
	        return true;
	    } else {
	        return false;
	    }
	}



	@Override
	public boolean updateComment(long commentId, long userId, long postId, String content) {
		boolean  check = false;
		if (commentRepo.countByUserIdAndPostIdAndCommentId(userId, postId, commentId) == 1) {
	        Optional<CommentEntity> optionalComment = commentRepo.findById(commentId);

	        if (optionalComment.isPresent()) {
	            CommentEntity commentEntity = optionalComment.get();
	            
	            // Update the comment content
	            commentEntity.setContent(content);
	            
	            // Save the updated comment
	            commentRepo.save(commentEntity);
	            check = true ;
	        } else {
	            check = false;
	        }
	    } else {
	       check = false;
	    }
		return check;
	}



	@Override
	public CommentEntity findByPostCommnentAndUserCommentUserID(PostEntity postCommnent, long userComment) {
		
		return commentRepo.findByPostCommnentAndUserCommentUserID(postCommnent, userComment);
	}



	@Override
	public Long countCommentsByPostId(long postId) {
		return commentRepo.countCommentsByPostId(postId);
	}

	@Override
	@Transactional
	public void deleteAllByPostId(long postId) {
		commentRepo.deleteAllByPostCommnentPostID(postId);
	}

}
