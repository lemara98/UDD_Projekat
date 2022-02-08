package com.ftn.mas.elasticSearch.Backend.controllers;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
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
import com.ftn.mas.elasticSearch.Backend.enums.BinaryOperator;
import com.ftn.mas.elasticSearch.Backend.model.CV;
import com.ftn.mas.elasticSearch.Backend.repositories.CVRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	
//	@Autowired
//	private CandidateRepository candidateRepository;
	
	@Autowired
	private CVRepository cvRepository;
	
	@Autowired
	private JestClient jestClient;
	
	private  Logger logger = LoggerFactory.getLogger(SearchController.class);

    @PostMapping("/2.1")
    public SearchHitsImpl<CV> getApplicationByNameAndLastName(@RequestBody BodyNameAndLastNameDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 Firstname and Lastname POST method");
    		SearchHitsImpl<CV> result = cvRepository.findCVByCandidatesFirstnameAndLastName(dto.firstName, dto.lastName);
        	logger.info("Got result from Elastic Search Repository (count): " + result.getSearchHits().size());
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
    public SearchHitsImpl<CV> getApplicationByEducation(@RequestBody BodyEducationDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.2 Education POST method");
    		SearchHitsImpl<CV> result = cvRepository.findCVByCandidatesEducation(dto.education.getEducation());
        	logger.info("Got result from Elastic Search Repository (count): " + result.getSearchHits().size());
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
    public SearchHitsImpl<CV> getApplicationByPDFText(@RequestBody BodyPDFFileTextDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.1 Content POST method");
    		SearchHitsImpl<CV> result = cvRepository.findCVByContent(dto.text);
        	logger.info("Got result from Elastic Search Repository (count): " + result.getSearchHits().size());
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
    public Object getApplicationByCombined(@RequestBody BodyCombinedDTO dto) throws Exception {
    	try {
    		logger.info("Entered /2.4 Boolean Query POST method");
//    		SearchHitsImpl<CV> result1 = cvRepository.findCVByCandidatesFirstnameAndLastName(dto.firstName, dto.lastName);
//    		SearchHitsImpl<CV> result2 = cvRepository.findCVByCandidatesEducation(dto.education.getEducation());
//    		SearchHitsImpl<CV> result3 = cvRepository.findCVByContent(dto.content);
    		
    		String query;
    		
    		
    		if (dto.operator1 == BinaryOperator.AND && dto.operator2 == BinaryOperator.AND) {
    			query = "{\r\n"
    					+ "  \"query\": {\r\n"
    					+ "    \"bool\": {\r\n"
    					+ "      \"should\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.firstname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.firstName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.lastname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.lastName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ],\r\n"
    					+ "      \"must\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"match\": {\r\n"
    					+ "            \"candidate.education\": \"" + dto.education + "\"\r\n"
    					+ "          }\r\n"
    					+ "        }, \r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"content.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.content + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ]\r\n"
    					+ "    }\r\n"
    					+ "  },\r\n"
    					+ "  \"highlight\": {\r\n"
    					+ "    \"fields\": {\r\n"
    					+ "      \"content.sr\": {}\r\n"
    					+ "    }\r\n"
    					+ "  }\r\n"
    					+ "}";
    		}
    		else if (dto.operator1 != BinaryOperator.AND && dto.operator2 == BinaryOperator.AND){
    			query = "{\r\n"
    					+ "  \"query\": {\r\n"
    					+ "    \"bool\": {\r\n"
    					+ "      \"should\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.firstname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.firstName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.lastname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.lastName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"match\": {\r\n"
    					+ "            \"candidate.education\": \"" + dto.education + "\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ],\r\n"
    					+ "      \"must\": [\r\n"
    					+ "         \r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"content.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.content + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ]\r\n"
    					+ "    }\r\n"
    					+ "  },\r\n"
    					+ "  \"highlight\": {\r\n"
    					+ "    \"fields\": {\r\n"
    					+ "      \"content.sr\": {}\r\n"
    					+ "    }\r\n"
    					+ "  }\r\n"
    					+ "}";
    		}
    		else if (dto.operator1 == BinaryOperator.AND && dto.operator2 != BinaryOperator.AND){
    			query = "{\r\n"
    					+ "  \"query\": {\r\n"
    					+ "    \"bool\": {\r\n"
    					+ "      \"should\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.firstname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.firstName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.lastname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.lastName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"content.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.content + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ],\r\n"
    					+ "      \"must\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"match\": {\r\n"
    					+ "            \"candidate.education\": \"" + dto.education + "\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ]\r\n"
    					+ "    }\r\n"
    					+ "  },\r\n"
    					+ "  \"highlight\": {\r\n"
    					+ "    \"fields\": {\r\n"
    					+ "      \"content.sr\": {}\r\n"
    					+ "    }\r\n"
    					+ "  }\r\n"
    					+ "}";
    		}
    		// if (dto.operator1 != BinaryOperator.AND && dto.operator2 != BinaryOperator.AND)
    		else {
    			query = "{\r\n"
    					+ "  \"query\": {\r\n"
    					+ "    \"bool\": {\r\n"
    					+ "      \"should\": [\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.firstname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.firstName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"candidate.lastname.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.lastName + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"query_string\": {\r\n"
    					+ "            \"default_field\": \"content.sr\",\r\n"
    					+ "            \"query\": \"*" + dto.content + "*\"\r\n"
    					+ "          }\r\n"
    					+ "        },\r\n"
    					+ "        {\r\n"
    					+ "          \"match\": {\r\n"
    					+ "            \"candidate.education\": \"" + dto.education + "\"\r\n"
    					+ "          }\r\n"
    					+ "        }\r\n"
    					+ "      ]\r\n"
    					+ "    }\r\n"
    					+ "  },\r\n"
    					+ "  \"highlight\": {\r\n"
    					+ "    \"fields\": {\r\n"
    					+ "      \"content.sr\": {}\r\n"
    					+ "    }\r\n"
    					+ "  }\r\n"
    					+ "}";
    		}
    		
    		SearchResult getResult = jestClient.execute(new Search.Builder(query).build());
    		
    		Gson gson = new GsonBuilder()
    	            .setPrettyPrinting()
    	            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    	            .create();
    		
    		var retVal = gson.toJson(getResult);
    		

        	logger.info("Got result from Elastic Search Repository");
        	return retVal;
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		logger.error(e.getLocalizedMessage());
    		logger.error("Something went wrong during retrieval of CV by BOOL QUERY");
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
    
    public class LocalDateAdapter implements JsonSerializer<LocalDate> {

    	@Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }

    }
    
//    private Set<CV> convertToHashSet(SearchHitsImpl<CV> result) {
//    	Set<CV> retVal = new HashSet<CV>();
//    	for(SearchHit<CV> sh : result.getSearchHits()) {
//    		retVal.add(sh.getContent());
//    	}
//    	return retVal;
//    }
//    
//    
//    private Set<CV> getFinalSetAfterSetOperations(BodyCombinedDTO dto, Set<CV> set1, Set<CV> set2, Set<CV> set3) {
//    	
//    	Set<CV> setTemp1 = new HashSet<CV>();
//		Set<CV> setTemp2 = new HashSet<CV>();
//		Set<CV> setFinal = new HashSet<CV>();
//    	
//    	if (dto.operator1 == BinaryOperator.AND) {
//			for (CV cv1 : set1) {
//				boolean isInside = false;
//				for (CV cv2 : set2) {
//    				if (cv1.equals(cv2)) {
//    					isInside = true;
//    					break;
//    				}
//    			}
//				if (isInside) setTemp1 .add(cv1);
//			}
//			
//		}
//		else {
//			if (set1.size() == 0) setTemp1 = set2;
//			for (CV cv1 : set1) {
//				boolean isNotInside = false;
//				for (CV cv2 : set2) {
//    				if (!cv1.equals(cv2)) {
//    					isNotInside = true;
//    					break;
//    				}
//    			}
//				if (isNotInside) setTemp1.add(cv1);
//			}
//		}
//		
//		if (dto.operator2 == BinaryOperator.AND) {
//			for (CV cv1 : setTemp1) {
//				boolean isInside = false;
//				for (CV cv2 : set3) {
//    				if (cv1.equals(cv2)) {
//    					isInside = true;
//    					break;
//    				}
//    			}
//				if (isInside) setTemp2.add(cv1);
//			}
//			
//		}
//		else {
//			if (setTemp1.size() == 0) setFinal = set3;
//			for (CV cv1 : setTemp1) {
//				boolean isNotInside = false;
//				for (CV cv2 : set3) {
//    				if (!cv1.equals(cv2)) {
//    					isNotInside = true;
//    					break;
//    				}
//    			}
//				if (isNotInside) setTemp2.add(cv1);
//			}
//		}
//		
//		setFinal.addAll(setTemp1);
//		
//		Set<CV> finalTemp = new HashSet<CV>();
//		
//		for (CV cv1 : setFinal) {
//			boolean isInside = false;
//			for (CV cv2 : setTemp2) {
//				if (cv1.equals(cv2)) {
//					isInside = true;
//					break;
//				}
//			}
//			if (!isInside) finalTemp.add(cv1);
//		}
//		
//		setFinal.addAll(finalTemp);
//		return setFinal;
//    }
    
    
    
    
    
    
    
    
    
    
}