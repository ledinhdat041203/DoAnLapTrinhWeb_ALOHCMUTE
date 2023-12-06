package vn.hcmute.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Entity.LikeEntity;
import vn.hcmute.Entity.PostEntity;
import vn.hcmute.Repository.LikeRepository;

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

	
}
