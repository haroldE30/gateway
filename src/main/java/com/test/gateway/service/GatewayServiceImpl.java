package com.test.gateway.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.gateway.model.Gateway;
import com.test.gateway.repository.GatewayRepository;

@Service
public class GatewayServiceImpl implements GatewayService{
	
	@Autowired
	private GatewayRepository gatewayRepository;
	
	
	public Gateway save(Gateway gateway) {
		return this.gatewayRepository.save(gateway);
	}

	public List<Gateway> getAllGateways() {
		return this.gatewayRepository.findAll();
	}

	public Optional<Gateway> getGateway(Long id) {
		return this.gatewayRepository.findById(id);
	}
}
