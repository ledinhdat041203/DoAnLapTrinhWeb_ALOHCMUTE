package vn.hcmute.Responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.GroupMembersEntity;


@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMembersEntity, Long>{
	public GroupMembersEntity findByUserMemberUserIDAndGroupGroupID(long userId, long groupId);
	List<GroupMembersEntity> findByGroupGroupID(Long groupID);
	int deleteByGroupGroupID(Long groupID);
	
}
