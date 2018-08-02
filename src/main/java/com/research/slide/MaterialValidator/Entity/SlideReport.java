package com.research.slide.MaterialValidator.Entity;

public class SlideReport {

	private int slideNo;
	private String header;
	private String footer;
	private String pageNumber;
	private String contrast;
	private String fontFamily;
	private String fontSize;
	private String maxSlideNo;
	private String position;
	private String words;
	private String bullters;
	private String picPositions;
	private String headertext;
	private String footertext;
	private String flag;
	private String summary;
	private int checkingPoints;
	

	public SlideReport() {
		
	}
	
	public SlideReport(int slideNo, String header, String footer, String pageNumber, String contrast, String fontFamily,
			String fontSize,String maxSlideNo,String position,String words,String bullters,String picPositions,String headertext,
			String footertext,String flag,String summary,int checkingPoints) {
		super();
		this.slideNo = slideNo;
		this.header = header;
		this.footer = footer;
		this.pageNumber = pageNumber;
		this.contrast = contrast;
		this.fontFamily = fontFamily;
		this.fontSize = fontSize;
		this.maxSlideNo = maxSlideNo;
		this.position = position;
		this.words = words;
		this.bullters= bullters;
		this.picPositions = picPositions;
		this.headertext = headertext;
		this.footertext = footertext;
		this.flag = flag;
		this.summary = summary;
		this.checkingPoints = checkingPoints;
	}

	public int getSlideNo() {
		return slideNo;
	}

	public void setSlideNo(int slideNo) {
		this.slideNo = slideNo;
	}

	public int getCheckingPoints() {
		return checkingPoints;
	}

	public void setCheckingPoints(int checkingPoints) {
		this.checkingPoints = checkingPoints;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPicPositions() {
		return picPositions;
	}

	public void setPicPositions(String picPositions) {
		this.picPositions = picPositions;
	}

	public String getHeadertext() {
		return headertext;
	}

	public void setHeadertext(String headertext) {
		this.headertext = headertext;
	}

	public String getFootertext() {
		return footertext;
	}

	public void setFootertext(String footertext) {
		this.footertext = footertext;
	}

	public String getPosition() {
		return position;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getBullters() {
		return bullters;
	}

	public void setBullters(String bullters) {
		this.bullters = bullters;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getContrast() {
		return contrast;
	}

	public void setContrast(String contrast) {
		this.contrast = contrast;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getMaxSlideNo() {
		return maxSlideNo;
	}

	public void setMaxSlideNo(String maxSlideNo) {
		this.maxSlideNo = maxSlideNo;
	}
	
	
	
}
