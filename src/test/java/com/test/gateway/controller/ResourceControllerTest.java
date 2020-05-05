package com.test.gateway.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.gateway.model.Device;
import com.test.gateway.model.Gateway;
import com.test.gateway.model.Status;
import com.test.gateway.service.DeviceService;
import com.test.gateway.service.GatewayService;
import com.test.gateway.validator.ResourceValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(ResourceController.class)
@Import(ResourceValidator.class)
public class ResourceControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private GatewayService gatewayService;
	
	@MockBean
	private DeviceService deviceService;
	
	@Test
	public void whenPostRequestToGatewaysAndValidGateway_thenCorrectResponse() throws Exception{
		Gateway gateway = new Gateway();
		gateway.setName("gateway 1");
		gateway.setIpAddress("192.168.0.01");
		
		mvc.perform(post(ResourceController.GATEWAY_MAPPING)
				.content(mapper.writeValueAsString(gateway))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void whenPostRequestToGatewaysAndInvalidGateway_thenCorrectResponse() throws Exception{
		Gateway gateway = new Gateway();
		gateway.setName("to be validated");
		gateway.setIpAddress("192.168.0.01");
		
		String asString = mapper.writeValueAsString(gateway);
		System.out.println(asString);
		
		
		mvc.perform(post(ResourceController.GATEWAY_MAPPING)
				.content(asString)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isExpectationFailed())
				.andExpect(content()
				        .contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void givenGateways_whenGetGateways_returnJsonArray() throws Exception{
		Gateway gateway = new Gateway();
		gateway.setName("g");
		gateway.setIpAddress("192.168.0.01");
		
		List<Gateway> gateways = new ArrayList<>();
		gateways.add(gateway);
		
		given(gatewayService.getAllGateways()).willReturn(gateways);
		
		mvc.perform(get(ResourceController.GATEWAY_MAPPING)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(1)));
		
	}
	
	@Test
	public void givenGateway_whenGetGatewayById_returnJson() throws Exception {
		Gateway gateway = new Gateway();
		gateway.setId(new Long(1));
		gateway.setName("gator");
		gateway.setIpAddress("192.168.0.01");
		
		Optional<Gateway> o = Optional.of(gateway);
		
		given(gatewayService.getGateway(1L)).willReturn(o);
		
		MvcResult mvcResult = mvc.perform(get(ResourceController.GATEWAY_MAPPING + "1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.name", is(gateway.getName())))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		Gateway readValue = mapper.readValue(content, Gateway.class);
		
		assertThat(gateway.getId(), equalTo(readValue.getId()));
		assertThat(gateway.getName(), equalTo(readValue.getName()));
		assertThat(gateway.getIpAddress(), equalTo(readValue.getIpAddress()));
		
	}
	
	@Test
	public void whenPostRequestToDevicesAndValidDevice_thenCorrectResonse() throws Exception{
		Gateway gateway = new Gateway();
		gateway.setName("bridge");
		gateway.setIpAddress("192.168.0.01");
		
		Calendar cal = Calendar.getInstance();
		
		Device device = new Device();
		device.setVendor("Samsung");
		device.setCreated(cal.getTime());
		device.setStatus(Status.OFFLINE);
		device.setGateway(gateway);
		
		mvc.perform(post(ResourceController.GATEWAY_MAPPING + "1" + "/device/")
				.content(mapper.writeValueAsString(device))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void whenDeleteRequestToDevices_thenCorrectResponse() throws Exception{
		mvc.perform(delete(ResourceController.GATEWAY_MAPPING + "/device/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

}
