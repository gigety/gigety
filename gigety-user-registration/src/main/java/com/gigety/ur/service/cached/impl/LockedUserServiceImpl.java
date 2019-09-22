package com.gigety.ur.service.cached.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.LockedUser;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.db.repo.LockedUserRepository;
import com.gigety.ur.service.cached.LockedUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LockedUserServiceImpl implements LockedUserService{

	private final LockedUserRepository lockedUserRepo;
	private final GigUserRepository userRepo;

	public LockedUserServiceImpl(LockedUserRepository lockedUserRepo, GigUserRepository userRepo) {
		super();
		this.lockedUserRepo = lockedUserRepo;
		this.userRepo = userRepo;
	}

	@Override
	@Caching(
		put = { @CachePut(value = "lockedUserCache", key = "#lockedUserId")},
		evict = { @CacheEvict(value="allLockedUserCache", allEntries = true )}
	)
	public LockedUser lockUser(Long lockedUserId,
			String lockEnforcerId) {
		log.debug("Locking user {} :: locked by {}", lockedUserId, lockEnforcerId);
		LockedUser lu = new LockedUser();
		GigUser lockedUser = userRepo.findById(lockedUserId).get();
		GigUser lockEnforcer = userRepo.findByEmail(lockEnforcerId);
		lu.setLockedUser(lockedUser);
		lu.setLockEnforcer(lockEnforcer);
		lu.setLockedUserName(lockedUser.getEmail());
		LockedUser u = lockedUserRepo.save(lu);
		log.debug("Locked:::: {}", u.toString());
		return u;
	}

	@Override
	public void unlockUser(Long lockedUserId) {
		// TODO Auto-generated method stub
		//TODO FINISH AND TEST UNLCOKING AND LOCKING AND CACHIONG _ GOOD JOB!!
	}

	@Override
	@Cacheable(value="lockedUserCache", key="#email")
	public boolean isUserLocked(String email) {
		log.debug("Searching locked users for {}", email);
		log.debug("LOCKED USERS:");
		lockedUserRepo.findAll().forEach(lockedUser -> log.debug(lockedUser.toString()));
		LockedUser lu = lockedUserRepo.findByLockedUserName(email);
		if(lu != null && lu.getLockedUserName().equals(email)) {
			return true;
		}
		return false;
	}

}
