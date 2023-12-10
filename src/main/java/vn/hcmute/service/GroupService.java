package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.GroupRepository;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.UserInfoEntity;



@Service
public class GroupService implements IGroupService{
	@Autowired
	GroupRepository groupRepo;

	@Override
	public List<GroupEntity> findAll() {
		return groupRepo.findAll();
	}

	@Override
	public Optional<GroupEntity> findById(Long id) {
		return groupRepo.findById(id);
	}

	@Override
	public <S extends GroupEntity> S save(S entity) {
		return groupRepo.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		groupRepo.deleteById(id);
	}

	@Override
	public List<GroupEntity> findGroupsByUserId(Long userId) {
		return groupRepo.findGroupsByUserId(userId);
	}
	
	
	
	

}
