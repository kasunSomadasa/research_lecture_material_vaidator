package com.research.slide.MaterialValidator;


import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.research.slide.MaterialValidator.Entity.SlideAdminTemplate;
import com.research.slide.MaterialValidator.Entity.SlideTemplateRepository;
import com.research.slide.MaterialValidator.Service.SlideService;

@SpringBootApplication
public class MaterialValidatorApplication implements CommandLineRunner{

	@Resource
	SlideService slideService;
	
	public static void main(String[] args) {
		SpringApplication.run(MaterialValidatorApplication.class, args);
		
	
	}
	@Override
	public void run(String...arg) throws Exception {
		slideService.deleteAll();
		slideService.init();
		
	}
}
