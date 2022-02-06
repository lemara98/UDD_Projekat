package com.ftn.mas.elasticSearch.Backend.model;

import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ftn.mas.elasticSearch.Backend.enums.Education;

@Document(indexName = "candidate")
public class Candidate {
	@Id
	public String id;
	public String firstname;
	public String lastname;
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	public LocalDate dateOfBirth;
	public String phoneNumber;
	public Education education;
	public Integer workingExperience;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
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
	public Integer getWorkingExperience() {
		return workingExperience;
	}
	public void setWorkingExperience(Integer workingExperience) {
		this.workingExperience = workingExperience;
	}
	
}
