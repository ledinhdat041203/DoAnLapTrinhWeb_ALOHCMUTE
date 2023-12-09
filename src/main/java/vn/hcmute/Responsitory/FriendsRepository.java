package vn.hcmute.Responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.FriendsEntity;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Long>{
	
	List<FriendsEntity> findByUser1UserID(long user1id);

	
	FriendsEntity findByUser1UserIDAndUser2UserID(long user1id, long user2id);


	List<FriendsEntity> findByUser2UserID(long user2id);

		
}