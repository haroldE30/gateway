package com.test.gateway.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.gateway.model.Device;
import com.test.gateway.model.Gateway;
import com.test.gateway.service.DeviceService;
import com.test.gateway.service.GatewayService;
import com.test.gateway.validator.ResourceValidator;

@RestController
@RequestMapping(ResourceController.GATEWAY_MAPPING)
public class ResourceController {

	public static final String GATEWAY_MAPPING = "/gateway/";
	
	@Autowired
	private GatewayService gatewayService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ResourceValidator validator;

	@PostMapping
	public ResponseEntity<Gateway> createGateway(@RequestBody Gateway gateway) {
		try {
			if (validator.areInputsNotValid(gateway)) {
				return new ResponseEntity<>(gateway, HttpStatus.EXPECTATION_FAILED);
			}
			
			Gateway savedGateway = this.gatewayService.save(gateway);
			return new ResponseEntity<>(savedGateway, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Gateway>> getAllGateways() {
		try {
			List<Gateway> gateways = gatewayService.getAllGateways();
			if (gateways.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(gateways, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Gateway> getGateway(@PathVariable Long id) {
		Optional<Gateway> gateway = gatewayService.getGateway(id);
		if (gateway.isPresent()) {
			return new ResponseEntity<>(gateway.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("{gatewayId}/device/")
	public ResponseEntity<Device> addDevice(@PathVariable Long gatewayId, @RequestBody Device device) {
		try {
			Device savedDevice = deviceService.saveDevice(gatewayId, device);
			return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@DeleteMapping("/device/{id}")
	public ResponseEntity<HttpStatus> deleteDevice(@PathVariable("id") Long id) {
		try {
			deviceService.deleteDevice(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
