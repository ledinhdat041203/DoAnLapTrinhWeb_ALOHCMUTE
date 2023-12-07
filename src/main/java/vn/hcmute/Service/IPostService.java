
package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;


public interface IPostService {

	List<PostModel> findByUserUserID(long userId, int page, int size);

	List<PostModel> getPostsByGroupId(long groupId, int page, int size);

	Optional<PostEntity> findById(Long id);

	<S extends PostEntity> S save(S entity);

	List<PostEntity> findAll();


}
