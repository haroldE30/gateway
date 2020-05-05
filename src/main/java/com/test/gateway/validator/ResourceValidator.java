package com.test.gateway.validator;

import org.springframework.stereotype.Component;

import com.test.gateway.model.Gateway;

@Component
public class ResourceValidator {
	
	private static final String TO_BE_VALIDATED = "to be validated";
	
	public boolean areInputsNotValid(Gateway gateway) {
		return (TO_BE_VALIDATED.equalsIgnoreCase(gateway.getName()) || 
				TO_BE_VALIDATED.equalsIgnoreCase(gateway.getIpAddress()));
	}
}
