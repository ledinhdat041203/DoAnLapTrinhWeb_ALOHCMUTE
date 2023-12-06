package vn.hcmute.Service;

import vn.hcmute.Entity.LikeEntity;
import vn.hcmute.Entity.PostEntity;

public interface ILikeService {

	<S extends LikeEntity> S save(S entity);

	//LikeEntity findLikeByPostAndUser(long postID, long userID);

	LikeEntity findLikeByPostAndUser(PostEntity post, long userID);


	//LikeEntity findByLikeByPostAndUser(long postID, long userID);
	//Optional<LikeEntity> findByPostAndUserLike(PostEntity post, UserInfoEntity userLike);

}
