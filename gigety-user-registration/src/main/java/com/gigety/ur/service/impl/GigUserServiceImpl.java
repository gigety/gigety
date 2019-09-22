package com.gigety.ur.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * 
 * GigUserService - service for registering gigety users.
 */
@Service
@Transactional
@Slf4j
public class GigUserServiceImpl implements GigUserService {

	private final GigUserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepo;
	private final PWResetTokenRepository pwResetTokenRepo;
	private final UserSecurityQuestionRepository userSecurityRepo;

	@Autowired
	public GigUserServiceImpl(GigUserRepository userRepo, PasswordEncoder passwordEncoder,
			VerificationTokenRepository verificationTokenRepo, PWResetTokenRepository pwResetTokenRepo,
			UserSecurityQuestionRepository userSecurityRepo) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenRepo = verificationTokenRepo;
		this.pwResetTokenRepo = pwResetTokenRepo;
		this.userSecurityRepo = userSecurityRepo;
	}

	@Override
	public GigUser registerNewUser(GigUser gigUser, UserSecurityQuestion userSecurityQuestion) throws EmailExistsException, Exception {
		try {
			if (emailExists(gigUser.getEmail())) {
				throw new EmailExistsException("An account already exists for " + gigUser.getEmail());
			}
			String encoded = passwordEncoder.encode(gigUser.getPassword());
			gigUser.setEnabled(false);
			gigUser.setPassword(encoded);
			gigUser.setPasswordConfirmation(encoded);
			log.debug("Saving user :::: {}", gigUser);
			gigUser = userRepo.save(gigUser);
			
			if(userSecurityQuestion != null) {
				userSecurityRepo.save(userSecurityQuestion);
			}
			return gigUser;
		} catch (Exception e) {
			log.error("Error registering new user in GigUserServiceImpl : {}", e.getMessage());
			throw new Exception("Error registering new user in GigUserServiceImpl : " + e.getMessage());
		}
	}

	@Override
	public GigUser updateExistingUser(GigUser gigUser) {
		//Optional<GigUser> optional = userRepo.findById(gigUser.getId());
		//GigUser user = optional.get();
		//TODO: Currently this updates ZERO fields. First lets determine best approach 
		//for merging updated fields. As for passwords just use changePassword
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
	public void assignVerificationToken(GigUser gigUser,
			String token) {
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
	public void assignResetPWToken(GigUser gigUser,
			String token) {
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
	public boolean changePassword(GigUser gigUser,
			String password) {
		try {
			Optional<GigUser> optional = userRepo.findById(gigUser.getId());
			GigUser user = optional.get();
			String encoded = passwordEncoder.encode(password);
			user.setPassword(encoded);
			user.setPasswordConfirmation(gigUser.getPassword());
			
			userRepo.save(user);
			// pwResetTokenRepo.deleteByGigUser(gigUser);
		} catch (Exception e) {
			log.error("Error updating password");
			throw new RuntimeException(String.format("Unable to update password with exception %s", e.getMessage()), e);
		}
		return true;
	}

	@Override
	public void removeUser(Long userId) {
		verificationTokenRepo.deleteByGigUserId(userId);
		pwResetTokenRepo.deleteByGigUserId(userId);
		userSecurityRepo.deleteByGigUserId(userId);
		userRepo.deleteById(userId);
	}

	@Override
	public Iterable<GigUser> findAll() {
		return userRepo.findAll();
	}

	@Override
	public List<GigUser> findByEmails(List<String> emails) {
		return userRepo.findByEmailIn(emails);
	}

	@Override
	public void lockUser(Long id, boolean lock) {
		userRepo.setLock(id, lock);
	}

	@Override
	public GigUser findById(Long id) {
		return userRepo.findById(id).get();
	}

}
