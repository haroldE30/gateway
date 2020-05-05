package com.test.gateway.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	ONLINE("online"),
	OFFLINE("offline");
	
	private String status;
	
	private Status(String status) {
		this.status = status;
	}
	
	@JsonValue
	public String getStatus() {
		return this.status;
	}
}
