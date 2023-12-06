package vn.hcmute.Service;

import java.util.List;
import java.util.Optional;

import vn.hcmute.Entity.ImageEntity;

public interface IImageService {

	Optional<ImageEntity> findById(Long id);

	List<ImageEntity> findAll();

	ImageEntity save(ImageEntity entity);

}
