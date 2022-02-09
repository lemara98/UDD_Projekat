package com.ftn.mas.elasticSearch.Backend.controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.mas.elasticSearch.Backend.enums.Education;
import com.ftn.mas.elasticSearch.Backend.model.CV;
import com.ftn.mas.elasticSearch.Backend.model.Candidate;
import com.ftn.mas.elasticSearch.Backend.repositories.CVRepository;
import com.ftn.mas.elasticSearch.Backend.repositories.CandidateRepository;
import com.ftn.mas.elasticSearch.Backend.services.FilesStorageService;

@RestController
@RequestMapping("/api/upload")
public class UploadPDFController {
	private static int num = 1;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CVRepository cvRepository;
	
	private  Logger logger = LoggerFactory.getLogger(UploadPDFController.class);
	
	@Autowired
	private FilesStorageService storageService;
	
	  @PostMapping("/cv")
	  public ResponseEntity<String> uploadFile(
			  @RequestParam("firstname") String firstname,
			  @RequestParam("lastname") String lastname,
			  @RequestParam("dateOfBirth") Date dateOfBirth,
			  @RequestParam("city") String city,
			  @RequestParam("phoneNumber") String phoneNumber,
			  @RequestParam("education") Education education,
			  @RequestParam("workingExperience") Integer workingExperience,
			  @RequestParam("cvDoc") MultipartFile file,
			  @RequestParam("geoPointLat") String geoPointLat,
			  @RequestParam("geoPointLon") String geoPointLon) {
	    String message = "";
	    try {
	    	// Setup an CV Object
	    	Candidate candidate = new Candidate();
	    	candidate.setId(String.valueOf(UploadPDFController.num));
	    	candidate.setFirstname(firstname);
	    	candidate.setLastname(lastname);
	    	candidate.setDateOfBirth(LocalDate.parse(dateOfBirth.toString()));
	    	candidate.setCity(city);
	    	candidate.setPhoneNumber(phoneNumber);
	    	candidate.setEducation(education);
	    	candidate.setWorkingExperience(workingExperience);
	    	
	    	GeoPoint geoPoint = new GeoPoint(Double.parseDouble(geoPointLat), Double.parseDouble(geoPointLon));
	    	
	    	// Uploading part
	    	storageService.save(file);
	    	message = "Uploaded the file successfully: " + file.getOriginalFilename();
	    	
	    	// PDF to Text parsing part 
	    	Path filepath = Paths.get("uploads");
	    	
	    	File f = new File(filepath + "/" + file.getOriginalFilename());
	    	String parsedText;
	    	PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
	    	parser.parse();
	    	COSDocument cosDoc = parser.getDocument();
	    	PDFTextStripper pdfStripper = new PDFTextStripper();
	    	PDDocument pdDoc = new PDDocument(cosDoc);
	    	parsedText = pdfStripper.getText(pdDoc);
	    	cosDoc.close();
	    	
	    	// Insert content to CV and Elastic Search
	    	logger.info(parsedText);
	    	CV cv = new CV();
	    	cv.setId(candidate.getId());
	    	cv.setCandidate(candidate);
	    	cv.setFileName(file.getOriginalFilename());
	    	cv.setGeoPoint(geoPoint);
	    	cv.setContent(parsedText);
	    	++UploadPDFController.num;
	    	cvRepository.save(cv);
	    	return ResponseEntity.status(HttpStatus.OK).body(parsedText);
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    }
	  }

}
