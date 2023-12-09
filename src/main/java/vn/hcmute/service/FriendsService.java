package vn.hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hcmute.Responsitory.FriendsRepository;
import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.UserInfoEntity;

@Service
public class FriendsService implements IFriendsService {
	@Autowired
	FriendsRepository friendsRepository;

	public FriendsService(FriendsRepository friendsRepository) {
		this.friendsRepository = friendsRepository;
	}

	@Override
	public <S extends FriendsEntity> S save(S entity) {
		return friendsRepository.save(entity);
	}

	@Override
	public List<FriendsEntity> findAll() {
		return friendsRepository.findAll();
	}

	@Override
	public <S extends FriendsEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
		return friendsRepository.findAll(example, pageable);
	}

	@Override
	public Optional<FriendsEntity> findById(Long id) {
		return friendsRepository.findById(id);
	}

	@Override
	public <S extends FriendsEntity> long count(Example<S> example) {
		return friendsRepository.count(example);
	}

	@Override
	public long count() {
		return friendsRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		friendsRepository.deleteById(id);
	}

	////////////////////////////////
	@Override
	public List<FriendsEntity> findByuser1userID(long user1id) {
		return friendsRepository.findByUser1UserID(user1id);
	}


	@Override
	public List<FriendsEntity> findByuser2userID(Long user2id) {
		return friendsRepository.findByUser2UserID(user2id);
	}
	
	@Override
	public FriendsEntity findByUser1IDAndUser2ID(long user1id, long user2id) {
		return friendsRepository.findByUser1UserIDAndUser2UserID(user1id, user2id);
	}

	@Override
	public void createFriendsByUser1AndUser2(UserInfoEntity user1, UserInfoEntity user2, boolean status) {
		FriendsEntity newFriends = new FriendsEntity();
		newFriends.setUser1(user1);
		newFriends.setUser2(user2);
		newFriends.setStatus(status);

		friendsRepository.save(newFriends);
	}
}
