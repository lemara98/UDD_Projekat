package com.ftn.mas.elasticSearch.Backend.controllers;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.mas.elasticSearch.Backend.dtos.BodyCombinedDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyEducationDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyGeoCodingDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyNameAndLastNameDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyPDFFileTextDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyPhraseQueryDTO;
import com.ftn.mas.elasticSearch.Backend.enums.Education;
import com.ftn.mas.elasticSearch.Backend.model.Candidate;
import com.ftn.mas.elasticSearch.Backend.repositories.CVRepository;
import com.ftn.mas.elasticSearch.Backend.repositories.CandidateRepository;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CVRepository cvRepository;
	
	private  Logger logger = LoggerFactory.getLogger(SearchController.class);

    @PostMapping("/2.1")
    public Collection<Candidate> getApplicationByNameAndLastName(@RequestBody BodyNameAndLastNameDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 getApplicationByNameAndLastName POST method");
    		Collection<Candidate> result = candidateRepository.findCandidateByFirstnameAndLastname(dto.firstName, dto.lastName);
        	logger.info("Got result from Elastic Search Repository (count): " + result.size());
        	return result;
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		logger.error(e.getLocalizedMessage());
    		logger.error("Something went wrong during retrieval of Candidates by firstName and lastName");
    		return null;
		}
    }

    @PostMapping("/2.2")
    public Collection<Candidate> getApplicationByEducation(@RequestBody BodyEducationDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 getApplicationByNameAndLastName POST method");
    		Collection<Candidate> result = candidateRepository.findByEducation(dto.education.getEducation());
        	logger.info("Got result from Elastic Search Repository (count): " + result.size());
        	return result;
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		logger.error(e.getLocalizedMessage());
    		logger.error("Something went wrong during retrieval of Candidates by firstName and lastName");
    		return null;
		}
    }
    
    @PostMapping("/2.3")
    public String getApplicationByPDFText(@RequestBody BodyPDFFileTextDTO dto) throws Exception {
        
    	return null;
    }
    
    @PostMapping("/2.4")
    public String getApplicationByCombined(@RequestBody BodyCombinedDTO dto) throws Exception {
        
    	return null;
    }
    
    @PostMapping("/2.5")
    public String getApplicationByPhrazeQuery(@RequestBody BodyPhraseQueryDTO dto) throws Exception {
        
    	return null;
    }
    
    @PostMapping("/2.8")
    public String getApplicationByGeoCoding(@RequestBody BodyGeoCodingDTO dto) throws Exception {
        
    	return null;
    }
}