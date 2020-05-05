package com.test.gateway.service;

import java.util.List;
import java.util.Optional;

import com.test.gateway.model.Gateway;

public interface GatewayService {

	Gateway save(Gateway gateway);

	List<Gateway> getAllGateways();

	Optional<Gateway> getGateway(Long id);
	
}
