package com.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.Entity.LikeEntity;
import com.hcmute.Entity.PostEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>{
	//Optional<LikeEntity> findByPostAndUserLike(PostEntity post, UserInfoEntity userLike);
	
	/*
	 * @Query("SELECT l FROM LikeEntity l WHERE l.userLike.userID = :conId and l.post.postID = :postId"
	 * ) LikeEntity findByPostAndUserLike(@Param("conId") Long
	 * receiverId, @Param("postId") Long postId);
	 */
	
	LikeEntity findByPostAndUserLikeUserID(PostEntity post, long userId);
}
