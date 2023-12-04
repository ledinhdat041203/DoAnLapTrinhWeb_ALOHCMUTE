package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.PostModel;



public interface IPostService {

	<S extends PostEntity> S save(S entity);

	List<PostModel> findAll();
	
	Optional<PostEntity> findById(Long id);

}
