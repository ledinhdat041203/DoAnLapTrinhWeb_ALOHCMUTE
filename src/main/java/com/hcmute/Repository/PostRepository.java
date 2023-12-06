package com.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.Entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
	 
}
