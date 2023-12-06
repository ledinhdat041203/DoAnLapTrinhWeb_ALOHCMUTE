package com.hcmute.Service;

import java.util.List;
import java.util.Optional;

import com.hcmute.Entity.PostEntity;
import com.hcmute.Models.PostModel;

public interface IPostService {

	<S extends PostEntity> S save(S entity);

	List<PostModel> findAll();

	Optional<PostEntity> findById(Long id);

}
