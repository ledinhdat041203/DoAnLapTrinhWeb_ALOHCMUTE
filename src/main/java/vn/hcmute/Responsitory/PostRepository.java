package vn.hcmute.Responsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
	Page<PostEntity> findByGroupPostGroupID(long groupId, Pageable pageable);
	
	
	List<PostEntity> findByUserUserID(long userId,Sort sortByDateDesc);
	
	PostEntity findByPostID(long postId);
	
	List<PostEntity> findByGroupPostGroupID(Long groupID);

	Page<PostEntity> findByUserUserID(long userId, Pageable pageable);
}
