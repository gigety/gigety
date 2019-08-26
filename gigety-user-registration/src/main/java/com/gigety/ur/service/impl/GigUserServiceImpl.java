package com.gigety.ur.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

@Service
@Transactional
public class GigUserServiceImpl implements GigUserService {

	private final GigUserRepository repo;
	
	@Autowired
	public GigUserServiceImpl(GigUserRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public GigUser registerNewUser(GigUser gigUser) throws EmailExistsException {
		if(emailExists(gigUser.getEmail())) {
			throw new EmailExistsException("An account already exists for " + gigUser.getEmail());
		}
		return repo.save(gigUser);
	}

	@Override
	public GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException {
		final GigUser emailOwner = repo.findByEmail(gigUser.getEmail());
		if(emailOwner != null && !emailOwner.getId().equals(gigUser.getId())) {
			throw new EmailExistsException("Operation not available for " + gigUser.getEmail());
		}
		return repo.save(gigUser);
	}

	private boolean emailExists(String email) {
		final GigUser user = repo.findByEmail(email);
		return user != null;
	}
}
