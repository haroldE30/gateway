package com.test.gateway.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.gateway.model.Gateway;
import com.test.gateway.repository.GatewayRepository;

@RunWith(SpringRunner.class)
public class GatewayServiceTest {
	
	@InjectMocks
	private GatewayServiceImpl gatewayService;
	
	@Mock
	private GatewayRepository gatewayRepository;
	
	@Test
	public void whenValidGateway_thenGatewayShouldBeCreated() throws Exception {
		Gateway g = new Gateway();
		g.setName("gateway 1");
		g.setIpAddress("192.168.0.01");
		
		when(gatewayRepository.save(any(Gateway.class))).thenReturn(g);
		
		Gateway savedGateway = gatewayService.save(g);
		assertThat(g.getName(), equalTo(savedGateway.getName()));
		assertThat(g.getIpAddress(), equalTo(savedGateway.getIpAddress()));
	}
	
	@Test
	public void whenGetGateways_returnGatewayList() throws Exception{
		Gateway gateway = new Gateway();
		gateway.setName("g");
		gateway.setIpAddress("192.168.0.01");
		
		List<Gateway> gateways = new ArrayList<>();
		gateways.add(gateway);
		
		when(gatewayRepository.findAll()).thenReturn(gateways);
		
		List<Gateway> list = gatewayService.getAllGateways();
		assertThat(list.size(), equalTo(1));
	}
	
	@Test
	public void whenGetGatewayById_returnGateway() throws Exception {
		Gateway gateway = new Gateway();
		gateway.setId(1L);
		gateway.setName("g");
		gateway.setIpAddress("192.168.0.01");
		
		Optional<Gateway> o = Optional.of(gateway);
		
		when(gatewayRepository.findById(anyLong())).thenReturn(o);
		
		Optional<Gateway> optional = gatewayService.getGateway(1L);
		assertThat(optional.isPresent(), equalTo(true));
		
		Gateway gateway2 = optional.get();
		assertThat(gateway.getId(), equalTo(gateway2.getId()));
		assertThat(gateway.getName(), equalTo(gateway2.getName()));
		assertThat(gateway.getIpAddress(), equalTo(gateway2.getIpAddress()));
	}
}
