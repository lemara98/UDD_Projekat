package com.ftn.mas.elasticSearch.Backend.model;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Document(indexName = "cv")
public class CV {
	// public String title;
	@Id
	private String id;
	@Field(type = FieldType.Nested, includeInParent = true)
	private Candidate candidate;
	@Field(type = FieldType.Text, analyzer = "serbian")
	private String content;
	@Field(type = FieldType.Text)
	private String fileName;
	@GeoPointField
	private GeoPoint geopoint;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public GeoPoint getGeopoint() {
		return geopoint;
	}
	public void setGeopoint(GeoPoint geopoint) {
		this.geopoint = geopoint;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) return false;
		
		if (obj.getClass() != this.getClass()) return false;
		
		CV cvObj = (CV) obj;
				
		return cvObj.id.equals(this.id) ;
	}
	
	
}
