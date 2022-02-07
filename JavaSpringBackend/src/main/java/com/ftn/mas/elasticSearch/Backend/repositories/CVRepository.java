package com.ftn.mas.elasticSearch.Backend.repositories;

import java.util.Collection;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.mas.elasticSearch.Backend.model.CV;

public interface CVRepository extends ElasticsearchRepository<CV, String> {

	@Query("{\"bool\":{\"must\":[{\"match\":{\"candidate.firstname\":\"?0\"}},{\"match\":{\"candidate.lastname\":\"?1\"}}]}}")
    Collection<CV> findCVByCandidatesFirstnameAndLastName(String firstname, String lastname);
	
	@Query("{\"match\":{\"candidate.education\":{\"query\":\"?0\"}}}")
    Collection<CV> findCVByCandidatesEducation(String education);
	
	@Query("{\"match\":{\"content.sr\":\"?0\"}}")
	Collection<CV> findCVByContent(String content);
}
