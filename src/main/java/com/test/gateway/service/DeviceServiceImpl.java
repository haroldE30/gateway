package com.test.gateway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.gateway.exception.MaximumLimitException;
import com.test.gateway.exception.ResourceNotFoundException;
import com.test.gateway.model.Device;
import com.test.gateway.model.Gateway;
import com.test.gateway.repository.DeviceRepository;
import com.test.gateway.repository.GatewayRepository;

@Service
public class DeviceServiceImpl implements DeviceService{
	
	private static final int MAX_LIMIT = 10;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private GatewayRepository gatewayRepository;
	
	public Device saveDevice(Long gatewayId, Device device) {
		Optional<Gateway> optional = gatewayRepository.findById(gatewayId);
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("Device with gateway id " + gatewayId + " not found.");
		}
		Gateway gateway = optional.get();
		
		if (gateway.getDevices().size() == MAX_LIMIT) {
			throw new MaximumLimitException("Maximum number of " + MAX_LIMIT + " devices is allowed.");
		}
		
		device.setGateway(gateway);
		return deviceRepository.save(device);
	}

	public void deleteDevice(Long id) {
		deviceRepository.deleteById(id);
	}
}
