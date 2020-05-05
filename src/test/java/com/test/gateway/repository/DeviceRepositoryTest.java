package com.test.gateway.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.gateway.model.Device;
import com.test.gateway.model.Gateway;
import com.test.gateway.model.Status;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeviceRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private DeviceRepository repository;
	
	@Autowired
	private GatewayRepository gatewayRepository;
	
	@Test
	public void whenValidDevice_thenSaveAndDeleteDevice() throws Exception {
		Gateway gateway = new Gateway();
		gateway.setId(1L);
		gateway.setName("g");
		gateway.setIpAddress("192.168.0.01");
		
		Gateway gateway2 = gatewayRepository.save(gateway);
		
		Calendar cal = Calendar.getInstance();
		
		Device device = new Device();
		device.setVendor("Vivo");
		device.setCreated(cal.getTime());
		device.setStatus(Status.ONLINE);
		device.setGateway(gateway2);
		
		Device saved = entityManager.persistAndFlush(device);
		assertNotNull(saved);
		assertThat(device.getVendor(), equalTo(saved.getVendor()));
		assertThat(device.getCreated(), equalTo(saved.getCreated()));
		assertThat(device.getStatus(), equalTo(saved.getStatus()));
		
		repository.delete(device);
	}
}
