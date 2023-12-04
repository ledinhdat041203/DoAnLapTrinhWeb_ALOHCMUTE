package vn.hcmute.service;

import vn.hcmute.entities.GroupMembersEntity;

public interface IGroupMemberService {

	GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);

	<S extends GroupMembersEntity> S save(S entity);

}
