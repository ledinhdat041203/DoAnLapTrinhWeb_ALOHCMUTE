package vn.hcmute.Service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.Entity.GroupEntity;

public interface IGroupService {

	List<GroupEntity> findAll();

	Optional<GroupEntity> findById(Long id);

}
