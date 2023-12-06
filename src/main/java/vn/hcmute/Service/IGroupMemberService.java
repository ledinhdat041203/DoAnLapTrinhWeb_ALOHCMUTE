package vn.hcmute.Service;

import vn.hcmute.Entity.GroupMembersEntity;

public interface IGroupMemberService {

	GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);

}
