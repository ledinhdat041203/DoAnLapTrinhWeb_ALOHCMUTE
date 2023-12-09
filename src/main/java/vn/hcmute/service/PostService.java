package vn.hcmute.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.LikeRepository;
import vn.hcmute.Responsitory.PostRepository;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;

@Service
public class PostService implements IPostService {
	@Autowired
	PostRepository postRepo;

	@Autowired
	IUserInfoService userInfoService;

	@Autowired
	ILikeService likeService;

	@Autowired
	LikeRepository likeRepo;

	private PostModel converEntityToModel(PostEntity post, long userid) {
		PostModel postModel = new PostModel();
		UserInfoEntity userInfo = userInfoService.findById(post.getUser().getUserID()).get();
		postModel.setPostID(post.getPostID());
		postModel.setContent(post.getContent());
		postModel.setGroupID(post.getGroupPost().getGroupID());
		postModel.setImageURL(post.getImage());
		postModel.setPostDate(post.getPostDate());
		postModel.setUserID(post.getUser().getUserID());
		postModel.setUserFullName(post.getUser().getFullName());
		postModel.setAvata(userInfo.getAvata());
		int likeCount = 0;
		List<LikeEntity> listLike = post.getListLikes();
		for (LikeEntity like : listLike) {
			if (like.isStatus()) {
				likeCount++;
			}
		}
		postModel.setLikeCount(likeCount);

		System.out.println(post.getPostID());
		System.out.println(userid);
		LikeEntity LikeEntity = likeRepo.findByPostAndUserLikeUserID(post, userid);
		if (LikeEntity == null || !LikeEntity.isStatus())
			postModel.setLiked(false);
		else
			postModel.setLiked(true);

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
	public List<PostModel> getPostsByGroupId(long groupId, int page, int size, long userid) {
		Pageable pageable = PageRequest.of(page, size);
		Page<PostEntity> postPage = postRepo.findByGroupPostGroupID(groupId, pageable);
		List<PostEntity> posts = postPage.getContent();
		List<PostModel> listPostModel = new ArrayList<>();
		for (PostEntity post : posts) {
			listPostModel.add(converEntityToModel(post, userid));
		}
		return listPostModel;
	}

	@Override
	public List<PostModel> findByUserUserID(long userId) {
		List<PostEntity> posts = postRepo.findByUserUserID(userId);

		List<PostModel> listPostModel = new ArrayList<>();
		for (PostEntity post : posts) {
			listPostModel.add(converEntityToModel(post, userId));
		}
		return listPostModel;
	}

	@Override
	public boolean existsById(Long id) {
		return postRepo.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		postRepo.deleteById(id);
	}
}
