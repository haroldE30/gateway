package com.test.gateway.service;

import com.test.gateway.model.Device;

public interface DeviceService {

	Device saveDevice(Long gatewayId, Device device);

	void deleteDevice(Long id);
	
}
