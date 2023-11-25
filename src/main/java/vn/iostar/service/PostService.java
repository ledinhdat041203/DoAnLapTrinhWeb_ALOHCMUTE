package vn.iostar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iostar.Responsitory.PostRepository;
import vn.iostar.entities.PostEntity;

@Service
public class PostService implements IPostService{
	@Autowired
	PostRepository postRepo;

	@Override
	public List<PostEntity> findAll() {
		return postRepo.findAll();
	}

	@Override
	public <S extends PostEntity> S save(S entity) {
		return postRepo.save(entity);
	}
	
	
}
