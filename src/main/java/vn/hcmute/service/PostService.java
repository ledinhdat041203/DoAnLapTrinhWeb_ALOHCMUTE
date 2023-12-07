package vn.hcmute.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.PostRepository;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.PostModel;

@Service
public class PostService implements IPostService {
	@Autowired
	PostRepository postRepo;

	private PostModel converEntityToModel(PostEntity post) {
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
		for (LikeEntity like : listLike) {
			if (like.isStatus()) {
				likeCount++;
			}
		}
		postModel.setLikeCount(likeCount);
		return postModel;
	}


	@Override
	public List<PostEntity> findAll() {
		return postRepo.findAll();
	}


	@Override
	public <S extends PostEntity> S save(S entity) {
		return postRepo.save(entity);
	}


	@Override
	public Optional<PostEntity> findById(Long id) {
		return postRepo.findById(id);
	}

	@Override
	public List<PostModel> getPostsByGroupId(long groupId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<PostEntity> postPage = postRepo.findByGroupPostGroupID(groupId, pageable);
		List<PostEntity> posts = postPage.getContent();
		List<PostModel> listPostModel = new ArrayList<>();
		for (PostEntity post : posts) {
			listPostModel.add(converEntityToModel(post));
		}
		return listPostModel;
	}

	@Override
	public List<PostModel> findByUserUserID(long userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<PostEntity> postPage = postRepo.findByUserUserID(userId, pageable);
		List<PostEntity> posts = postPage.getContent();
		List<PostModel> listPostModel = new ArrayList<>();
		for (PostEntity post : posts) {
			listPostModel.add(converEntityToModel(post));
		}
		return listPostModel;
	}

}
