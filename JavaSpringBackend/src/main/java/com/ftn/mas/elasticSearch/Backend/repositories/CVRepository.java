package com.ftn.mas.elasticSearch.Backend.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.mas.elasticSearch.Backend.model.CV;

public interface CVRepository extends ElasticsearchRepository<CV, String> {

}
