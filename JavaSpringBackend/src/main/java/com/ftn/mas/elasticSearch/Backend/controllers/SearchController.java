package com.ftn.mas.elasticSearch.Backend.controllers;

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

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @PostMapping("/2.1")
    public String getApplicationByNameAndLastName(@RequestBody BodyNameAndLastNameDTO dto) throws Exception {
		
    	return null;
    }

    @PostMapping("/2.2")
    public String getApplicationByEducation(@RequestBody BodyEducationDTO dto) throws Exception {
        
    	return null;
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