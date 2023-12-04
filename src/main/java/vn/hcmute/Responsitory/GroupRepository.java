package vn.hcmute.Responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.GroupEntity;


@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

}
