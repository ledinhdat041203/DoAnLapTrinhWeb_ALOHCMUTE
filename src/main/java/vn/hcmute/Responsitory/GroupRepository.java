package vn.hcmute.Responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.UserInfoEntity;


@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{
}
