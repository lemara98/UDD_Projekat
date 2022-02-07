package com.ftn.mas.elasticSearch.Backend.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.common.util.set.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.mas.elasticSearch.Backend.dtos.BodyCombinedDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyEducationDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyGeoCodingDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyNameAndLastNameDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyPDFFileTextDTO;
import com.ftn.mas.elasticSearch.Backend.dtos.BodyPhraseQueryDTO;
import com.ftn.mas.elasticSearch.Backend.enums.BinaryOperator;
import com.ftn.mas.elasticSearch.Backend.model.CV;
import com.ftn.mas.elasticSearch.Backend.repositories.CVRepository;
import com.ftn.mas.elasticSearch.Backend.repositories.CandidateRepository;
import com.google.common.base.Objects;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CVRepository cvRepository;
	
	private  Logger logger = LoggerFactory.getLogger(SearchController.class);

    @PostMapping("/2.1")
    public Collection<CV> getApplicationByNameAndLastName(@RequestBody BodyNameAndLastNameDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 Firstname and Lastname POST method");
    		Collection<CV> result = cvRepository.findCVByCandidatesFirstnameAndLastName(dto.firstName, dto.lastName);
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
    public Collection<CV> getApplicationByEducation(@RequestBody BodyEducationDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.2 Education POST method");
    		Collection<CV> result = cvRepository.findCVByCandidatesEducation(dto.education.getEducation());
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
    public Collection<CV> getApplicationByPDFText(@RequestBody BodyPDFFileTextDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 Content POST method");
    		Collection<CV> result = cvRepository.findCVByContent(dto.text);
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
    
    @PostMapping("/2.4")
    public Collection<CV> getApplicationByCombined(@RequestBody BodyCombinedDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.4 Boolean Query POST method");
    		Collection<CV> result1 = cvRepository.findCVByCandidatesFirstnameAndLastName(dto.firstName, dto.lastName);
    		Collection<CV> result2 = cvRepository.findCVByCandidatesEducation(dto.education.getEducation());
    		Collection<CV> result3 = cvRepository.findCVByContent(dto.content);
    		
    		Set<CV> set1 = new HashSet<CV>(result1);
    		Set<CV> set2 = new HashSet<CV>(result2);
    		Set<CV> set3 = new HashSet<CV>(result3);
    		Set<CV> setTemp1 = new HashSet<CV>();
    		Set<CV> setFinal = new HashSet<CV>();
    		
    		if (dto.operator1 == BinaryOperator.AND) {
    			for (CV cv1 : set1) {
    				boolean isInside = false;
    				for (CV cv2 : set2) {
        				if (cv1.equals(cv2)) {
        					isInside = true;
        					break;
        				}
        			}
    				if (isInside) setTemp1 .add(cv1);
    			}
    			
    		}
    		else {
    			for (CV cv1 : set1) {
    				boolean isNotInside = false;
    				for (CV cv2 : set2) {
        				if (!cv1.equals(cv2)) {
        					isNotInside = true;
        					break;
        				}
        			}
    				if (isNotInside) setTemp1.add(cv1);
    			}
    		}
    		
    		if (dto.operator2 == BinaryOperator.AND) {
    			for (CV cv1 : setTemp1) {
    				boolean isInside = false;
    				for (CV cv2 : set3) {
        				if (cv1.equals(cv2)) {
        					isInside = true;
        					break;
        				}
        			}
    				if (isInside) setFinal.add(cv1);
    			}
    			
    		}
    		else {
    			for (CV cv1 : setTemp1) {
    				boolean isNotInside = false;
    				for (CV cv2 : set3) {
        				if (!cv1.equals(cv2)) {
        					isNotInside = true;
        					break;
        				}
        			}
    				if (isNotInside) setFinal.add(cv1);
    			}
    		}
    		
    		Collection<CV> resultFinal = setFinal;
        	logger.info("Got result from Elastic Search Repository (count): " + resultFinal.size());
        	return resultFinal;
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		logger.error(e.getLocalizedMessage());
    		logger.error("Something went wrong during retrieval of Candidates by firstName and lastName");
    		return null;
		}
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