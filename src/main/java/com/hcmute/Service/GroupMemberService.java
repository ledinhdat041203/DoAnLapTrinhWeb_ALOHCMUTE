package com.hcmute.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmute.Entity.GroupMembersEntity;
import com.hcmute.Repository.GroupMemberRepository;

@Service
public class GroupMemberService implements IGroupMemberService{
	@Autowired
	GroupMemberRepository groupMemberRepo;

	@Override
	public GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId) {
		return groupMemberRepo.findByUserMemberUserIDAndGroupGroupID(userId, groupId);
	}
	
	
}
