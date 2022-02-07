package com.ftn.mas.elasticSearch.Backend;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.ftn.mas.elasticSearch.Backend.services.FilesStorageService;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BackendApplication {
	
	@Resource FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Override
	  public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	  }

}
