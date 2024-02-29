package vn.hcmute.Responsitory;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import vn.hcmute.entities.CommentEntity;
import vn.hcmute.entities.PostEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	// Optional<LikeEntity> findByPostAndUserLike(PostEntity post, UserInfoEntity
	// userLike);

	/*
	 * @Query("SELECT l FROM LikeEntity l WHERE l.userLike.userID = :conId and l.post.postID = :postId"
	 * ) LikeEntity findByPostAndUserLike(@Param("conId") Long
	 * receiverId, @Param("postId") Long postId);
	 */
	@Query(value = "SELECT * FROM comments c WHERE c.postid = :postId ORDER BY c.commentDate DESC", nativeQuery = true)
	List<CommentEntity> findByPostIDContaining(@Param("postId") long postId);

	@Modifying
	@Query(value = "INSERT INTO comments (postid,userid, content, commentdate) VALUES (:postId,:userId, :content, :commentDate)", nativeQuery = true)
	void createComment(@Param("postId") long postId, @Param("userId") long userId, @Param("content") String content,
			@Param("commentDate") Date commentDate);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM comments WHERE commentid = :commentId", nativeQuery = true)
	void deleteCommentById(@Param("commentId") long commentId);

	@Query(value = "SELECT COUNT(c.userid) FROM comments c WHERE c.userid = :userid AND c.postid = :postid AND c.commentid = :commentid", nativeQuery = true)
	Integer countByUserIdAndPostIdAndCommentId(@Param("userid") Long userId, @Param("postid") Long postId,
			@Param("commentid") Long commentId);

	@Query(value = "SELECT COUNT(c.commentid) FROM comments c WHERE c.postid = :postId", nativeQuery = true)
	Long countCommentsByPostId(@Param("postId") long postId);

	CommentEntity findByPostCommnentAndUserCommentUserID(PostEntity postCommnent, long userComment);

	void deleteAllByPostCommnentPostID(long postId);

}