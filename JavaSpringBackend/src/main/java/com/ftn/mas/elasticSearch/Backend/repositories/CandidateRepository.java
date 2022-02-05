package com.ftn.mas.elasticSearch.Backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.mas.elasticSearch.Backend.enums.Education;
import com.ftn.mas.elasticSearch.Backend.model.Candidate;

public interface CandidateRepository extends ElasticsearchRepository<Candidate, String> {
	
	Page<Candidate> findByNameAndLastName(String name, String lastName, Pageable pageable);
	
	Page<Candidate> findByEducation(Education education, Pageable pageable);
}
