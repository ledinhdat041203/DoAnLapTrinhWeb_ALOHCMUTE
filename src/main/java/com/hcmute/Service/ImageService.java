package com.hcmute.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.hcmute.Entity.ImageEntity;
import com.hcmute.Repository.ImageRepository;

@Service
public class ImageService implements IImageService {
	
	@Autowired
	ImageRepository imageRepository;

	@Override
	public ImageEntity save(ImageEntity entity) {
		return imageRepository.save(entity);
	}

	@Override
	public List<ImageEntity> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Optional<ImageEntity> findById(Long id) {
		return imageRepository.findById(id);
	}
	
}
