package com.research.slide.MaterialValidator.Entity;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocTemplateRepository extends MongoRepository<AdminDocTemplate,String>{

	Collection<AdminDocTemplate> findByUniversityLike(String name);
	Collection<AdminDocTemplate> findByNameLike(String name);
	Collection<AdminDocTemplate> findByActualNameLike(String name);
	
}
