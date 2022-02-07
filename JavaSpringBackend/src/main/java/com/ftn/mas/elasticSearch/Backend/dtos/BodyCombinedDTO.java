package com.ftn.mas.elasticSearch.Backend.dtos;

import com.ftn.mas.elasticSearch.Backend.enums.BinaryOperator;
import com.ftn.mas.elasticSearch.Backend.enums.Education;

public class BodyCombinedDTO {
	public String firstName;
	public String lastName;
	public Education education;
	public String content;
	
	public BinaryOperator operator1;
	public BinaryOperator operator2;
}
