package com.gigety.web.api.db.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gigety.web.api.db.sql.model.OauthProvider;
import com.gigety.web.api.db.sql.repo.OauthProviderRepository;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private OauthProviderRepository oAUthRepo;
	private static Logger LOG = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Test
	public void whenFindByEmail_thenReturnUser() {
		OauthProvider samo = oAUthRepo.findByName("samo").get();
		System.out.println("samo :: " + samo.getName());
		LOG.info("SAMO: " + samo.getName());
		assertThat(samo.getName().equals("samo"));
	}
}
