package com.research.slide.MaterialValidator.Entity;

public class SlideStandard {

	private String materialName,masterFirstName,masterSubName;
	private int audianceSize;
	
	
	
	public SlideStandard() {
		super();
	}



	public SlideStandard(String materialName, String masterFirstName, String masterSubName, int audianceSize) {
		super();
		this.materialName = materialName;
		this.masterFirstName = masterFirstName;
		this.masterSubName = masterSubName;
		this.audianceSize = audianceSize;
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



	public String getMasterSubName() {
		return masterSubName;
	}



	public void setMasterSubName(String masterSubName) {
		this.masterSubName = masterSubName;
	}



	public int getAudianceSize() {
		return audianceSize;
	}



	public void setAudianceSize(int audianceSize) {
		this.audianceSize = audianceSize;
	}
	
}
