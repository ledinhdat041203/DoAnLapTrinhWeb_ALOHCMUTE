package vn.hcmute.service;

import java.util.List;

import vn.hcmute.entities.GroupMembersEntity;

public interface IGroupMemberService {

	GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);

	<S extends GroupMembersEntity> S save(S entity);

	List<GroupMembersEntity> findByGroupGroupID(Long groupID);

	void deleteById(Long id);

	void delete(GroupMembersEntity entity);

	int deleteByGroupGroupID(Long groupID);

}
