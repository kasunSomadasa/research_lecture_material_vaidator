package com.research.slide.MaterialValidator.Entity;

public class SlideTemplate {

	private int noOfWords;
	private int noOfBullert;
	private int noOfSlide;
	
	private String fontFamily;
	private String titleTextPosition;
	private String contentPoistion;
	
	private String backColor;
	private String textColor;
	
	private Boolean slideNumbers;
	private Boolean header;
	private Boolean footer;
	
	
	public SlideTemplate() {
	
	}
	
	public SlideTemplate( int noOfWords, int noOfBullert, int noOfSlide,
			String fontFamily, String titleTextPosition, String contentPoistion,
			Boolean slideNumbers, Boolean header, Boolean footer) {
		super();
	
		this.noOfWords = noOfWords;
		this.noOfBullert = noOfBullert;
		this.noOfSlide = noOfSlide;
		this.fontFamily = fontFamily;
		this.titleTextPosition = titleTextPosition;
		this.contentPoistion = contentPoistion;
		this.backColor = backColor;
		this.textColor = textColor;
		this.slideNumbers = slideNumbers;
		this.header = header;
		this.footer = footer;
	}


	public int getNoOfWords() {
		return noOfWords;
	}

	public void setNoOfWords(int noOfWords) {
		this.noOfWords = noOfWords;
	}

	public int getNoOfBullert() {
		return noOfBullert;
	}

	public void setNoOfBullert(int noOfBullert) {
		this.noOfBullert = noOfBullert;
	}

	public int getNoOfSlide() {
		return noOfSlide;
	}

	public void setNoOfSlide(int noOfSlide) {
		this.noOfSlide = noOfSlide;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getTitleTextPosition() {
		return titleTextPosition;
	}

	public void setTitleTextPosition(String titleTextPosition) {
		this.titleTextPosition = titleTextPosition;
	}

	public String getContentPoistion() {
		return contentPoistion;
	}

	public void setContentPoistion(String contentPoistion) {
		this.contentPoistion = contentPoistion;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public Boolean getSlideNumbers() {
		return slideNumbers;
	}

	public void setSlideNumbers(Boolean slideNumbers) {
		this.slideNumbers = slideNumbers;
	}

	public Boolean getHeader() {
		return header;
	}

	public void setHeader(Boolean header) {
		this.header = header;
	}

	public Boolean getFooter() {
		return footer;
	}

	public void setFooter(Boolean footer) {
		this.footer = footer;
	}
	
	
	
}
