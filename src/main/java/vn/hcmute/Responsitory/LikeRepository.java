package vn.hcmute.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;


@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>{
	//Optional<LikeEntity> findByPostAndUserLike(PostEntity post, UserInfoEntity userLike);
	
	/*
	 * @Query("SELECT l FROM LikeEntity l WHERE l.userLike.userID = :conId and l.post.postID = :postId"
	 * ) LikeEntity findByPostAndUserLike(@Param("conId") Long
	 * receiverId, @Param("postId") Long postId);
	 */
	
	LikeEntity findByPostAndUserLikeUserID(PostEntity post, long userId);

	Optional<LikeEntity> findByPostPostIDAndUserLikeUserID(long postid, long userId);

	void deleteAllByPostPostID(long postid);

}
