package vn.iostar.Responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entities.UserEntity;

public interface UserResponsitory extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByemailContaining(String email);
}
