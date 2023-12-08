package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.UserInfoEntity;

@Service
public interface IFriendsService {

	void deleteById(Long id);

	long count();

	<S extends FriendsEntity> long count(Example<S> example);

	Optional<FriendsEntity> findById(Long id);

	<S extends FriendsEntity> Page<S> findAll(Example<S> example, Pageable pageable);

	List<FriendsEntity> findAll();

	<S extends FriendsEntity> S save(S entity);
	
	//Hàm tự tạo
	
	List<FriendsEntity> findByuser1userID(long user1id);

	FriendsEntity findByUser1IDAndUser2ID(long user1id, long user2id);
	
	void createFriendsByUser1AndUser2(UserInfoEntity user1, UserInfoEntity user2, boolean status);

	List<FriendsEntity> findByuser2userID(Long user2id);
	

}
