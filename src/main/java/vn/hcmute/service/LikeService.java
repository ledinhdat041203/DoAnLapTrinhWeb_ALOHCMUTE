package vn.hcmute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.LikeRepository;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;



@Service
public class LikeService implements ILikeService{
	@Autowired
	LikeRepository likeRepo;

	@Override
	public <S extends LikeEntity> S save(S entity) {
		return likeRepo.save(entity);
	}


	@Override
	public LikeEntity findLikeByPostAndUser(PostEntity post, long userID) {
		return likeRepo.findByPostAndUserLikeUserID(post, userID);
	}


	@Override
	public void deleteAllByPostPostId(long postId) {
		likeRepo.deleteAllByPostPostID(postId);
	}

	
}
