package vn.hcmute.service;

import java.sql.Date;
import java.util.List;

import vn.hcmute.entities.CommentEntity;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.CommentModel;

public interface ICommentService {

	<S extends CommentEntity > S save(S entity);

	//LikeEntity findLikeByPostAndUser(long postID, long userID);

	LikeEntity findLikeByPostAndUser(PostEntity post, long userID);


    List<CommentEntity>  findCommentByPost(long post);
    void createComment(long postId,long userId,String content,Date createdate);

	void createComment(long postId, long userId, String content, java.util.Date createdate);
    boolean deleteComment(long commentId,long userId, long postId);
    boolean updateComment(long commentId,long userId, long postId,String content);
    CommentEntity findByPostCommnentAndUserCommentUserID(PostEntity postCommnent, long userComment);

	Long countCommentsByPostId(long postId);

	void deleteAllByPostId(long postId);

	CommentModel convertEntityToModel(CommentEntity comment);

	
}
