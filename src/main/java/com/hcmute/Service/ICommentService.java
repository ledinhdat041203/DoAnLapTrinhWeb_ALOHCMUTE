package com.hcmute.Service;

import com.hcmute.Entity.LikeEntity;
import com.hcmute.Entity.PostEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.hcmute.Entity.CommentEntity;

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
