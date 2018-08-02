package com.research.slide.MaterialValidator.Entity;

public class DocReport {

	private String header,footer,template,family;

	public DocReport() {
	}
	
	public DocReport(String header, String footer, String template, String family) {
		super();
		this.header = header;
		this.footer = footer;
		this.template = template;
		this.family = family;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}
	
}
