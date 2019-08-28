package com.gigety.ur.security;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.repo.GigUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GigUserDetailService implements UserDetailsService {

	private final GigUserRepository userRepo;
	private static final String ROLE_USER = "ROLE_USER";
	
	public GigUserDetailService(GigUserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final GigUser user = userRepo.findByEmail(username);
		if(user == null) {
			log.debug("No user found with email/username " + username);
			throw new UsernameNotFoundException("No user found with email/username " + username); 
		}
		final User springSecurityUser = new User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(ROLE_USER));
		log.debug("Spring Security User (converted) : {}", springSecurityUser);
		return springSecurityUser;
		
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
	
}
