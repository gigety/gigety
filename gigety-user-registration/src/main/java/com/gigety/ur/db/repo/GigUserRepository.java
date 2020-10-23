package com.gigety.ur.db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gigety.ur.db.model.GigUser;

public interface GigUserRepository extends JpaRepository<GigUser, Long> {
	
	GigUser findByEmail(String email);
	List<GigUser> findByEmailIn(List<String> emails);
	void deleteById(String id);
	
	//Make sure to use @Modifying @Query updates in a transaction
	@Modifying
	@Query("update GigUser gu set gu.locked = ?2 where gu.id = ?1")
	void setLock(Long id, boolean lock);
}
