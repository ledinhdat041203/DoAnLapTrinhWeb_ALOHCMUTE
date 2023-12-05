package vn.iostar.service;

import java.util.List;

import vn.iostar.entities.PostEntity;


public interface IPostService {

	<S extends PostEntity> S save(S entity);

	List<PostEntity> findAll();

}
