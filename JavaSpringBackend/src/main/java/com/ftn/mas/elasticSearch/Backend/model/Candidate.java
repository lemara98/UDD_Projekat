package com.ftn.mas.elasticSearch.Backend.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.ftn.mas.elasticSearch.Backend.enums.Education;

@Document(indexName = "candidate")
public class Candidate {
	@Id
	public String id;
	public String name;
	public String lastName;
	public Date dateOfBirth;
	public String phoneNumber;
	public Education education;
	public int workingExperience;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}
	public int getWorkingExperience() {
		return workingExperience;
	}
	public void setWorkingExperience(int workingExperience) {
		this.workingExperience = workingExperience;
	}
}
