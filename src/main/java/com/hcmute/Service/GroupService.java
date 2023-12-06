package com.hcmute.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.GroupEntity;
import com.hcmute.Repository.GroupRepository;

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
	
	
	
	

}
