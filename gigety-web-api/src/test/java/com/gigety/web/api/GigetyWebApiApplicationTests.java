package com.gigety.web.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.db.repo.OauthProviderRepository;
import com.gigety.web.api.db.repo.UserRepositoryTest;

@SpringBootTest
public class GigetyWebApiApplicationTests {

	@Test
	public void contextLoads() {
	}

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
