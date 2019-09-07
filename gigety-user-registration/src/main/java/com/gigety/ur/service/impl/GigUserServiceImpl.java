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
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.db.repo.PWResetTokenRepository;
import com.gigety.ur.db.repo.UserSecurityQuestionRepository;
import com.gigety.ur.db.repo.VerificationTokenRepository;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal
 * 
 * GigUserService - service for registering gigety users.
 */
@Service
@Transactional
@Slf4j
public class GigUserServiceImpl implements GigUserService {

	private final GigUserRepository userRepo;
	private final BCryptPasswordEncoder pwEncoder;
	private final VerificationTokenRepository verificationTokenRepo;
	private final PWResetTokenRepository pwResetTokenRepo;
	private final UserSecurityQuestionRepository userSecurityRepo;


	public GigUserServiceImpl(GigUserRepository userRepo, BCryptPasswordEncoder pwEncoder,
			VerificationTokenRepository verificationTokenRepo, PWResetTokenRepository pwResetTokenRepo,
			UserSecurityQuestionRepository userSecurityRepo) {
		super();
		this.userRepo = userRepo;
		this.pwEncoder = pwEncoder;
		this.verificationTokenRepo = verificationTokenRepo;
		this.pwResetTokenRepo = pwResetTokenRepo;
		this.userSecurityRepo = userSecurityRepo;
	}

	@Override
	public GigUser registerNewUser(GigUser gigUser) throws EmailExistsException, Exception {
		try {
		if (emailExists(gigUser.getEmail())) {
			throw new EmailExistsException("An account already exists for " + gigUser.getEmail());
		}
		String encoded = pwEncoder.encode(gigUser.getPassword());
		gigUser.setEnabled(false);
		gigUser.setPassword(encoded);
		gigUser.setPasswordConfirmation(encoded);
		log.debug("Saving user :::: {}",gigUser);
		gigUser =  userRepo.save(gigUser);
		userSecurityRepo.save(gigUser.getUserSecurityQuestion());
		return gigUser;
		}catch(Exception e) {
			log.error("Error registering new user in GigUserServiceImpl : {}", e.getMessage());
			throw new Exception("Error registering new user in GigUserServiceImpl : " + e.getMessage());
		}
	}

	@Override
	public GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException {
		final GigUser emailOwner = userRepo.findByEmail(gigUser.getEmail());
		if (emailOwner != null && !emailOwner.getId().equals(gigUser.getId())) {
			throw new EmailExistsException("Operation not available for " + gigUser.getEmail());
		}
		return userRepo.save(gigUser);
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		VerificationToken vToke = verificationTokenRepo.findByToken(token);
		// TODO Revisit password and password confirmation validations, this set up is
		// causing issues i do not like
		vToke.getGigUser().setPasswordConfirmation(vToke.getGigUser().getPassword());
		log.debug("Found verification token :: {}", vToke);
		return vToke;
	}

	@Override
	public PWResetToken getPWRestToken(String token) {
		PWResetToken pToke = pwResetTokenRepo.findByToken(token);
		log.debug("Found pwRestToken {}", pToke);
		return pToke;

	}

	@Override
	public GigUser saveRegisteredUser(GigUser gigUser) {
		log.debug("Saving registered user {}", gigUser);
		verificationTokenRepo.deleteByGigUser(gigUser);
		return userRepo.save(gigUser);
	}

	private boolean emailExists(String email) {
		final GigUser user = userRepo.findByEmail(email);
		return user != null;
	}

	@Override
	public GigUser findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void assignVerificationToken(GigUser gigUser, String token) {
		VerificationToken vToken = new VerificationToken(token, gigUser);
		// TODO: Research Date best practices for java
		Instant now = Instant.now(); // current date
		Instant after = now.plus(Duration.ofDays(1));
		Date dateAfter = Date.from(after);
		vToken.setExpiryDate(dateAfter);
		log.debug("Set verification token : {} for user : {}", token, gigUser);
		verificationTokenRepo.save(vToken);

	}

	@Override
	public void assignResetPWToken(GigUser gigUser, String token) {
		PWResetToken pwResetOken = new PWResetToken(token, gigUser);
		// TODO: Research Date best practices for java
		Instant now = Instant.now(); // current date
		Instant after = now.plus(Duration.ofDays(1));
		Date dateAfter = Date.from(after);
		pwResetOken.setExpiryDate(dateAfter);
		log.debug("Set pwReset token : {} for user : {}", token, gigUser);
		pwResetTokenRepo.save(pwResetOken);

	}

	/**
	 * Update user password
	 */
	@Override
	public boolean changePassword(GigUser gigUser, String password) {
		try {
			gigUser.setPassword(pwEncoder.encode(password));
			gigUser.setPasswordConfirmation(gigUser.getPassword());
			userRepo.save(gigUser);
		} catch (Exception e) {
			log.error("Error updating password");
			throw new RuntimeException(String.format("Unable to update password with exception %s", e.getMessage()), e);
		}
		return true;
	}

}
