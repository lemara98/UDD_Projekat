package com.ftn.mas.elasticSearch.Backend.repositories;

import java.util.Collection;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.mas.elasticSearch.Backend.model.Candidate;

public interface CandidateRepository extends ElasticsearchRepository<Candidate, String> {
	
	Collection<Candidate> findCandidateByFirstnameAndLastname(String firstname, String lastname);
	
	Collection<Candidate> findByEducation(String education);
}
