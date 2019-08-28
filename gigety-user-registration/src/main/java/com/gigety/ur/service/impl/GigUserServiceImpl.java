package com.gigety.ur.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.PWResetToken;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.db.repo.PWResetTokenRepository;
import com.gigety.ur.db.repo.VerificationTokenRepository;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

import lombok.extern.slf4j.Slf4j;

/**
 * GigUserService - service for registering gigety users.
 * 
 * @author samuelsegal
 *
 */
@Service
@Transactional
@Slf4j
public class GigUserServiceImpl implements GigUserService {

	private final GigUserRepository userEmail;
	private final BCryptPasswordEncoder pwEncoder;
	private final VerificationTokenRepository verificationTokenRepo;
	private final PWResetTokenRepository pwResetTokenRepo;

	@Autowired
	public GigUserServiceImpl(GigUserRepository userEmail, BCryptPasswordEncoder pwEncoder,
			VerificationTokenRepository verificationTokenRepo, PWResetTokenRepository pwResetTokenRepo) {
		super();
		this.userEmail = userEmail;
		this.pwEncoder = pwEncoder;
		this.verificationTokenRepo = verificationTokenRepo;
		this.pwResetTokenRepo = pwResetTokenRepo;
	}


	@Override
	public GigUser registerNewUser(GigUser gigUser) throws EmailExistsException {
		if (emailExists(gigUser.getEmail())) {
			throw new EmailExistsException("An account already exists for " + gigUser.getEmail());
		}
		String encoded = pwEncoder.encode(gigUser.getPassword());
		gigUser.setEnabled(false);
		gigUser.setPassword(encoded);
		gigUser.setPasswordConfirmation(encoded);
		return userEmail.save(gigUser);
	}


	@Override
	public GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException {
		final GigUser emailOwner = userEmail.findByEmail(gigUser.getEmail());
		if (emailOwner != null && !emailOwner.getId().equals(gigUser.getId())) {
			throw new EmailExistsException("Operation not available for " + gigUser.getEmail());
		}
		return userEmail.save(gigUser);
	}


	@Override
	public VerificationToken getVerificationToken(String token) {
		VerificationToken vToke = verificationTokenRepo.findByToken(token);
		//TODO Revisit password and password confirmation validations, this set up is causing issues i do not like
		vToke.getGigUser().setPasswordConfirmation(vToke.getGigUser().getPassword());
		log.debug("Found token :: {}", vToke);
		return vToke;
	}

	@Override
	public GigUser saveRegisteredUser(GigUser gigUser) {
		log.debug("Saving registered user {}", gigUser);
		verificationTokenRepo.deleteByGigUser(gigUser);
		return userEmail.save(gigUser);
	}

	private boolean emailExists(String email) {
		final GigUser user = userEmail.findByEmail(email);
		return user != null;
	}

	@Override
	public GigUser findByEmail(String email) {
		return userEmail.findByEmail(email);
	}

	@Override
	public void assignVerificationToken(GigUser gigUser, String token) {
		VerificationToken vToken = new VerificationToken(token, gigUser);
		// TODO: Research Date best practices for java
		Instant now = Instant.now(); //current date
		Instant after= now.plus(Duration.ofDays(1));
		Date dateAfter = Date.from(after);
		vToken.setExpiryDate(dateAfter);
		log.debug("Set verification token : {} for user : {}", token, gigUser);
		verificationTokenRepo.save(vToken);
		
	}

	@Override
	public void assignResetPWToken(GigUser gigUser, String token) {
		PWResetToken pwResetOken = new PWResetToken(token, gigUser);
		// TODO: Research Date best practices for java
		Instant now = Instant.now(); //current date
		Instant after= now.plus(Duration.ofDays(1));
		Date dateAfter = Date.from(after);
		pwResetOken.setExpiryDate(dateAfter);
		log.debug("Set pwReset token : {} for user : {}", token, gigUser);
		pwResetTokenRepo.save(pwResetOken);
		
	}

}
