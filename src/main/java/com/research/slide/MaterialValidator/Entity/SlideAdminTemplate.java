package com.research.slide.MaterialValidator.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SlideTemplates")
public class SlideAdminTemplate {

	@Id
	private String id;
	private String name;
	private String actualName;
	private String university;
	
	
	public SlideAdminTemplate() {
	}
	
	public SlideAdminTemplate( String name, String actualName, String university) {
		super();
		
		this.name = name;
		this.actualName = actualName;
		this.university = university;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}
	
	
	
}
