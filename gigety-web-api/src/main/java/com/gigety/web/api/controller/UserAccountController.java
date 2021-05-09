package com.gigety.web.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gigety.web.api.db.mongo.entity.UserAccount;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.service.UserAccountService;
import com.gigety.web.api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAccount")
@Slf4j
public class UserAccountController {

	private final UserAccountService userAccountService;
	private final UserService userService;
	/**
	 * Currently this method is not used, however i the future we may want user to create an account manually.
	 * Currently They are created dynamically in getUserAccount
	 * @param userPrincipal
	 * @param userAccount
	 * @param result
	 * @return
	 */
	public UserAccount createUserAccount(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserAccount userAccount, BindingResult result) {
		
		UserAccount ua = userAccountService.createUserAccount(userAccount);
		log.debug("Created UserAccount {}", userAccount);
		return ua;
	}
	
	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public UserAccount getUserAccount(@CurrentUser UserPrincipal userPrincipal) throws GigetyException {
		UserAccount ua = null;
		try {
			ua = userAccountService.getUserAccount(String.valueOf(userPrincipal.getEmail()));
			log.debug("Found User Account :: {}", ua);
			
			return ua;
		}
		catch(ResourceNotFoundException e) {
			UserAccount userAccount = new UserAccount();
			log.warn("No user Account for user {}, Creating a new account for {}", userPrincipal.getEmail(), userPrincipal.getUsername());
			userAccount.setMysqlUserId(String.valueOf(userPrincipal.getId()));
			userAccount.setEmail(userPrincipal.getEmail());
			userAccount.setUserName(userPrincipal.getName());
			String imageUrl = userService.findUserImagerUrlById(userPrincipal.getId());
			userAccount.setImageUrl(imageUrl);
			ua = userAccountService.createUserAccount(userAccount);
			log.debug("Created UserAccount {}", userAccount);
			return ua;
		}catch(Exception e) {
			log.error("Unknown Error {}", e.getMessage(), e);
			throw new GigetyException(e.getMessage());
		}
	}
	
	@PostMapping(value = "/update")
	public UserAccount updateUserAccount(@CurrentUser UserPrincipal userPrincipal, UserAccount userAccount) throws GigetyException{
		try {
			UserAccount ua = userAccountService.updateUserAccount(userAccount);
			log.debug("Updated UserAccount ::  {}",ua);
			return ua;
		}catch(Exception e) {
			log.error("Error updating User :: {}", e.getMessage(), e);
			throw new GigetyException("Error Updaitng User Account for user " + userPrincipal.getEmail()); 
		}
	}
	
	
	
}
