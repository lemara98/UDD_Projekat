package com.ftn.mas.elasticSearch.Backend.repositories;

import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.mas.elasticSearch.Backend.model.CV;

public interface CVRepository extends ElasticsearchRepository<CV, String> {

	@Query("{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"candidate.firstname.sr\",\"query\":\"*?0*\"}},{\"query_string\":{\"default_field\":\"candidate.lastname.sr\",\"query\":\"*?1*\"}}]}}\r\n")
	SearchHitsImpl<CV> findCVByCandidatesFirstnameAndLastName(String firstname, String lastname);
	
	@Query("{\"match\":{\"candidate.education\":{\"query\":\"?0\"}}}")
	SearchHitsImpl<CV> findCVByCandidatesEducation(String education);
	
	@Query("{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"content.sr\",\"query\":\"*?0*\"}}]}}\r\n")
	@Highlight(fields = {@HighlightField(name = "content.sr")})
	SearchHitsImpl<CV> findCVByContent(String content);
}
