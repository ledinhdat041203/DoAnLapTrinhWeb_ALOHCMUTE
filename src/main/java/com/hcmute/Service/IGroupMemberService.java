package com.hcmute.Service;

import com.hcmute.Entity.GroupMembersEntity;

public interface IGroupMemberService {

	GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);

}
