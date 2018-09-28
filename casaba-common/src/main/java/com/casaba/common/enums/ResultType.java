package com.casaba.common.enums;

public enum ResultType {

	OK("OK"),
	NOTOK("NOTOK");
	
	private String value;
	ResultType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
