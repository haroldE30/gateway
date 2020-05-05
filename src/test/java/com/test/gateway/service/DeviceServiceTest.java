package com.test.gateway.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.gateway.model.Device;
import com.test.gateway.model.Gateway;
import com.test.gateway.model.Status;
import com.test.gateway.repository.DeviceRepository;
import com.test.gateway.repository.GatewayRepository;

@RunWith(SpringRunner.class)
public class DeviceServiceTest {
	
	@InjectMocks
	private DeviceServiceImpl deviceService;
	
	@Mock
	private DeviceRepository deviceRepository;
	
	@Mock
	private GatewayRepository gatewayRepository;
	
	@Test
	public void whenValidGateway_thenDeviceShouldBeCreated() throws Exception {
		Calendar cal = Calendar.getInstance();
		
		Device device = new Device();
		device.setVendor("Samsung");
		device.setCreated(cal.getTime());
		device.setStatus(Status.OFFLINE);
		
		Gateway gateway = new Gateway();
		gateway.setId(1L);
		gateway.setName("g");
		gateway.setIpAddress("192.168.0.01");
		
		List<Device> devices = new ArrayList<>();
		devices.add(device);
		
		gateway.setDevices(devices);
		
		Optional<Gateway> o = Optional.of(gateway);
		
		when(gatewayRepository.findById(anyLong())).thenReturn(o);
		
		when(deviceRepository.save(any(Device.class))).thenReturn(device);
		
		Device savedDevice = deviceService.saveDevice(1L, device);
		assertThat(device.getCreated(), equalTo(savedDevice.getCreated()));
		assertThat(device.getVendor(), equalTo(savedDevice.getVendor()));
		assertThat(device.getStatus(), equalTo(savedDevice.getStatus()));
	}
}
