package vn.hcmute.Responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.GroupEntity;


@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{
	 @Query("SELECT g FROM GroupEntity g JOIN g.listMembers m WHERE m.userMember.userID = :userId")
	    List<GroupEntity> findGroupsByUserId(@Param("userId") Long userId);
}
