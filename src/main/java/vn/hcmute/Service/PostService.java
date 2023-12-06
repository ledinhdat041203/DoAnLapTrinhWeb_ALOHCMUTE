package vn.hcmute.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Entity.GroupEntity;
import vn.hcmute.Entity.LikeEntity;
import vn.hcmute.Entity.PostEntity;
import vn.hcmute.Models.PostModel;
import vn.hcmute.Repository.PostRepository;

@Service
public class PostService implements IPostService{
	@Autowired
	PostRepository postRepo;

	@Override
	public List<PostModel> findAll() {
		List<PostEntity> list = postRepo.findAll();
		List<PostModel> listPostModel = new ArrayList<>();
		 for (PostEntity post : list) {
			 
			 PostModel postModel = new PostModel();
			 postModel.setPostID(post.getPostID());
			 postModel.setContent(post.getContent());
			 postModel.setGroupID(post.getGroupPost().getGroupID());
			 postModel.setImageURL(post.getImage());
			 postModel.setPostDate(post.getPostDate());
			 postModel.setUserID(post.getUser().getUserID());
			 postModel.setUserFullName(post.getUser().getFullName());
			 int likeCount = 0;
			 List<LikeEntity> listLike = post.getListLikes();
			 for(LikeEntity like : listLike) {
				 if(like.isStatus()) {
					 likeCount++;
				 }
			 }
			 postModel.setLikeCount(likeCount);
			 listPostModel.add(postModel);
			 
		 }
		return listPostModel;
	}

	@Override
	public <S extends PostEntity> S save(S entity) {
		return postRepo.save(entity);
	}

	@Override
	public Optional<PostEntity> findById(Long id) {
		return postRepo.findById(id);
	}
	
	
	
	
}
