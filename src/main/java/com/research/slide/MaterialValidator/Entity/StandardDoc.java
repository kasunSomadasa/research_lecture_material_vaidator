package com.research.slide.MaterialValidator.Entity;

public class StandardDoc {

	private String materialName;
	private String masterFirstName;
	
	public StandardDoc() {
	}
	
	public StandardDoc(String materialName, String masterFirstName) {
		super();
		this.materialName = materialName;
		this.masterFirstName = masterFirstName;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMasterFirstName() {
		return masterFirstName;
	}
	public void setMasterFirstName(String masterFirstName) {
		this.masterFirstName = masterFirstName;
	}
	
}
