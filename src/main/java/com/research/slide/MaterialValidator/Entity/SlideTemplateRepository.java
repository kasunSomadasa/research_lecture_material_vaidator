package com.research.slide.MaterialValidator.Entity;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideTemplateRepository extends MongoRepository<SlideAdminTemplate,String>{

	Collection<SlideAdminTemplate> findByUniversityLike(String name);
	Collection<SlideAdminTemplate> findByNameLike(String name);
	Collection<SlideAdminTemplate> findByActualNameLike(String name);
	
	
}
