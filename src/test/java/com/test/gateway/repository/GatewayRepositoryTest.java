package com.test.gateway.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.gateway.model.Gateway;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GatewayRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private GatewayRepository repository;
	
	@Test
	public void whenValidGateway_thenSaveAndReturnGateway() throws Exception {
		Gateway gateway = new Gateway();
		gateway.setName("gateway 1");
		gateway.setIpAddress("192.168.0.01");
		
		entityManager.persistAndFlush(gateway);
		
		Optional<Gateway> optional = repository.findById(1L);
		assertThat(optional.isPresent(), equalTo(true));
		Gateway gateway2 = optional.get();
		assertThat(1L, equalTo(gateway2.getId()));
		assertThat(gateway.getName(), equalTo(gateway2.getName()));
		assertThat(gateway.getIpAddress(), equalTo(gateway2.getIpAddress()));
		
		List<Gateway> list = repository.findAll();
		assertThat(list.size(), equalTo(1));
	}
}
