package vn.hcmute.Service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.Entity.PostEntity;
import vn.hcmute.Models.PostModel;

public interface IPostService {

	<S extends PostEntity> S save(S entity);

	List<PostModel> findAll();

	Optional<PostEntity> findById(Long id);

}
