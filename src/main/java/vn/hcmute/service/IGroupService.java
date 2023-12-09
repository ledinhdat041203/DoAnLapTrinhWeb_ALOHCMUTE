package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.entities.GroupEntity;



public interface IGroupService {

	List<GroupEntity> findAll();

	Optional<GroupEntity> findById(Long id);

	<S extends GroupEntity> S save(S entity);

	void deleteById(Long id);

	List<GroupEntity> findGroupsByUserId(Long userId);


}
