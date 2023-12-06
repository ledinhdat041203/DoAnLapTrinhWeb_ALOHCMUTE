package vn.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.Entity.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

}
