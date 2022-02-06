package com.ftn.mas.elasticSearch.Backend.enums;

public enum Education {
	Osnovno("Osnovno"), 					// 0
	Srednje("Srednje"), 					// 1
	Vise("Vise"),							// 2
	Visoko("Visoko"),						// 3
	Specijalisticko("Specijalisticko");		// 4
	
	private final String education;

	Education(String education) {
		this.education = education;
	}

	public String getEducation() {
		return education;
	}
}
