package vn.hcmute.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.Entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long>{


}
