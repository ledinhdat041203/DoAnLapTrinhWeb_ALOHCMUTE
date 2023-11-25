package vn.iostar.Responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iostar.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{

}
