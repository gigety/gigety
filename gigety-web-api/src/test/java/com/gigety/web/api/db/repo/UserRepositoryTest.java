package com.gigety.web.api.db.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gigety.web.api.db.model.OauthProvider;

@RunWith(SpringRunner.class)
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
