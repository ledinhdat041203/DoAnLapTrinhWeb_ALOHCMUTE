package vn.hcmute.service;

import java.util.List;

import vn.hcmute.entities.PostEntity;
import vn.hcmute.model.PostModel;

public interface IPostService {

	List<PostModel> findAll();

	List<PostModel> findByUserUserID(Long id);	
	
}
