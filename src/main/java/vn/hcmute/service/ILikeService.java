package vn.hcmute.service;

import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;

public interface ILikeService {

	<S extends LikeEntity> S save(S entity);

	//LikeEntity findLikeByPostAndUser(long postID, long userID);

	LikeEntity findLikeByPostAndUser(PostEntity post, long userID);

	void deleteAllByPostPostId(long postId);


	//LikeEntity findByLikeByPostAndUser(long postID, long userID);
	//Optional<LikeEntity> findByPostAndUserLike(PostEntity post, UserInfoEntity userLike);

}
