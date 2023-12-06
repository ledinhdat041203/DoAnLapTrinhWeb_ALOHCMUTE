package com.hcmute.Service;

import java.util.List;
import java.util.Optional;

import com.hcmute.Entity.GroupEntity;

public interface IGroupService {

	List<GroupEntity> findAll();

	Optional<GroupEntity> findById(Long id);

}
