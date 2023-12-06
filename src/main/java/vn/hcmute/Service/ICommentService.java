package vn.hcmute.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import vn.hcmute.Entity.CommentEntity;
import vn.hcmute.Entity.LikeEntity;
import vn.hcmute.Entity.PostEntity;

public interface ICommentService {

	<S extends CommentEntity > S save(S entity);

	//LikeEntity findLikeByPostAndUser(long postID, long userID);

	LikeEntity findLikeByPostAndUser(PostEntity post, long userID);


    List<CommentEntity>  findCommentByPost(long post);
    void createComment(long postId,long userId,String content,Date createdate);

	void createComment(long postId, long userId, String content, java.util.Date createdate);
    void deleteComment(long commentId,long userId, long postId);
    void updateComment(long commentId,long userId, long postId,String content);
}
