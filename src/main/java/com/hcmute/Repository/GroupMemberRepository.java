package com.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.Entity.GroupMembersEntity;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMembersEntity, Long>{
	public GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);
}
