package com.research.slide.MaterialValidator.Entity;

public class CustomDoc {

    private String fontfamily;
	private String header;
	private String footer;
	private String numbering;
	private String materialName;
	private String masterFirstName;
	
	
	public CustomDoc() {

	}
	
	public CustomDoc(String fontfamily, String header, String footer, String numbering, String materialName,
			String masterFirstName) {
		super();
		this.fontfamily = fontfamily;
		this.header = header;
		this.footer = footer;
		this.numbering = numbering;
		this.materialName = materialName;
		this.masterFirstName = masterFirstName;
	}

	public String getFontfamily() {
		return fontfamily;
	}

	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
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

	public String getNumbering() {
		return numbering;
	}

	public void setNumbering(String numbering) {
		this.numbering = numbering;
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
