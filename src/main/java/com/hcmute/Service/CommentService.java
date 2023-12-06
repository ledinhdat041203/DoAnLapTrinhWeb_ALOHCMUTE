package com.hcmute.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.PostEntity;
import com.hcmute.Entity.UserInfoEntity;
import com.hcmute.Entity.CommentEntity;
import com.hcmute.Entity.LikeEntity;
import com.hcmute.Repository.CommentRepository;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentRepository commentRepo;

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



	@Override
	public void deleteComment(long commentId, long userId, long postId) {
		if( commentRepo.countByUserIdAndPostIdAndCommentId(userId, postId, commentId)  == 1) {
			 commentRepo.deleteById(commentId);
			 System.out.println("Thành công");
		}
		else
		{
			 System.out.println("Không tồn tại bản ghi để xóa.");
		}
		
	}



	@Override
	public void updateComment(long commentId, long userId, long postId, String content) {
	    if (commentRepo.countByUserIdAndPostIdAndCommentId(userId, postId, commentId) == 1) {
	        Optional<CommentEntity> optionalComment = commentRepo.findById(commentId);

	        if (optionalComment.isPresent()) {
	            CommentEntity commentEntity = optionalComment.get();
	            
	            // Update the comment content
	            commentEntity.setContent(content);
	            

	            // Save the updated comment
	            commentRepo.save(commentEntity);
	        } else {
	            System.out.println("Không tồn tại bản ghi để cập nhật.");
	        }
	    } else {
	        System.out.println("Không tồn tại bản ghi để xóa.");
	    }
	}





}
