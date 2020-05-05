package com.test.gateway.exception;

public class MaximumLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MaximumLimitException(String msg) {
		super(msg);
	}
}
