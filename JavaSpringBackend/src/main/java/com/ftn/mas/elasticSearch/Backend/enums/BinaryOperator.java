package com.ftn.mas.elasticSearch.Backend.enums;

public enum BinaryOperator {
	AND("AND"),		// 0
	OR("OR");		// 1
	
	private final String operator;

	BinaryOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}
}
