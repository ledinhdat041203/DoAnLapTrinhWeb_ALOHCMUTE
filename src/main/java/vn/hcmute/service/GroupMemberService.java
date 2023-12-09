package vn.hcmute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.GroupMemberRepository;
import vn.hcmute.entities.GroupMembersEntity;



@Service
public class GroupMemberService implements IGroupMemberService{
	@Autowired
	GroupMemberRepository groupMemberRepo;

	@Override
	public GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId) {
		return groupMemberRepo.findByUserMemberUserIDAndGroupGroupID(userId, groupId);
	}

	@Override
	public <S extends GroupMembersEntity> S save(S entity) {
		return groupMemberRepo.save(entity);
	}

	@Override
	public List<GroupMembersEntity> findByGroupGroupID(Long groupID) {
		return groupMemberRepo.findByGroupGroupID(groupID);
	}

	@Override
	public void deleteById(Long id) {
		groupMemberRepo.deleteById(id);
	}

	@Override
	public void delete(GroupMembersEntity entity) {
		groupMemberRepo.delete(entity);
	}

	@Override
	public int deleteByGroupGroupID(Long groupID) {
		return groupMemberRepo.deleteByGroupGroupID(groupID);
	}

	
	
}
