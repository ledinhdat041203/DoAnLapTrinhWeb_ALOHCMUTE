package com.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.Entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long>{


}
