package vn.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.Entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
	 
}
