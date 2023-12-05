package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.PostModel;



public interface IPostService {

	<S extends PostEntity> S save(S entity);

	List<PostModel> findAll();
	
	Optional<PostEntity> findById(Long id);

	List<PostModel> getPostsByGroupId(long groupId, int page, int size);

}
