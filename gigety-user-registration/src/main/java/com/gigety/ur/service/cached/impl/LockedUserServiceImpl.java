package com.gigety.ur.service.cached.impl;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
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
@Transactional
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
		evict = { @CacheEvict(value="lockedUserCache", key = "#email")}
	)
	public LockedUser lockUser(String email,
			String lockEnforcerId) {
		log.debug("Locking user {} :: locked by {}", email, lockEnforcerId);
		LockedUser lu = new LockedUser();
		GigUser lockedUser = userRepo.findByEmail(email);
		GigUser lockEnforcer = userRepo.findByEmail(lockEnforcerId);
		lu.setLockedUser(lockedUser);
		lu.setLockEnforcer(lockEnforcer);
		lu.setLockedUserName(lockedUser.getEmail());
		LockedUser u = lockedUserRepo.save(lu);
		log.debug("Locked:::: {}", u.toString());
		return u;
	}

	@Override
	@Caching(
		evict = { @CacheEvict(value="lockedUserCache", key = "#email")}
	)
	public void unlockUser(String email) {
		log.debug("Unlocking user {}", email);
		lockedUserRepo.deleteByLockedUserName(email);
	}

	@Override
	@Cacheable(value="lockedUserCache", key="#email")
	public boolean isUserLocked(String email) {
		log.debug("Searching locked users for {}", email);
		log.debug("LOCKED USERS:");
		lockedUserRepo.findAll().forEach(lockedUser -> log.debug(lockedUser.toString()));
		LockedUser lu = lockedUserRepo.findByLockedUserName(email);
		log.debug("Found LOCKED USER - {}", lu);
		if(lu != null && lu.getLockedUserName().equals(email)) {
			return true;
		}
		return false;
	}

}
