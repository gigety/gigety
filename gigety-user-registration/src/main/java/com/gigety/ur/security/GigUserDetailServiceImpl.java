package com.gigety.ur.security;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.Role;
import com.gigety.ur.db.repo.GigUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GigUserDetailServiceImpl implements UserDetailsService {

	private final GigUserRepository userRepo;
	private static final String ROLE_USER = "ROLE_USER";
	
	public GigUserDetailServiceImpl(GigUserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final GigUser user = userRepo.findByEmail(username);
		log.debug("GigUser Login :: {}", user);
		if(user == null) {
			log.debug("No user found with email/username " + username);
			throw new UsernameNotFoundException("No user found with email/username " + username); 
		}
		
		final User springSecurityUser = new User(
											user.getEmail(), 
											user.getPassword(), 
											user.getEnabled(), 
											true, true, true, 
											getAuthorities(user.getRoles()));
		log.debug("Spring Security User (converted) : {}", springSecurityUser);
		return springSecurityUser;
		
	}

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles){
//		return Arrays.asList(new SimpleGrantedAuthority(ROLE_USER));
//		Collection<? extends GrantedAuthority> priveleges = roles.stream()
//				.flatMap(role -> role.getPrivileges().stream())
//				.map(p -> new SimpleGrantedAuthority(p.getName()))
//				.collect(Collectors.toList());
		Collection<? extends GrantedAuthority> roleList = roles.stream()
				.map(r -> new SimpleGrantedAuthority(r.getName()))
				.collect(Collectors.toList());	
		roleList.forEach(role->log.debug("ROLE: {}", role.getAuthority()));
		return  roleList;
	}
	
}
