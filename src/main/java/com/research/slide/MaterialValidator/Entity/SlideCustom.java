package com.research.slide.MaterialValidator.Entity;

public class SlideCustom {
	
	private String materialName;
	private String masterFirstName;
	private String masterSubName;
	private int noofslide;
	private int noofword;
	private int bullert;
	private int caudiance;
	private String fontfamily;
	private String position;
	private String header;
	private String headertext;
	private String footer;
	private String footertext;
	private String numbering;
	private String bcolorcheck;
	private String bcolor;
	private String fcolorcheck;
	private String fcolor;
	
	public SlideCustom() {
	}
	
	public SlideCustom(int noofslide, int noofword, int bullert, int caudiance, String fontfamily, String position,
			String header, String headertext, String footer, String footertext, String numbering, String bcolorcheck,
			String bcolor, String fcolorcheck, String fcolor,String materialName, String masterFirstName, String masterSubName) {
		super();
		this.noofslide = noofslide;
		this.noofword = noofword;
		this.bullert = bullert;
		this.caudiance = caudiance;
		this.fontfamily = fontfamily;
		this.position = position;
		this.header = header;
		this.headertext = headertext;
		this.footer = footer;
		this.footertext = footertext;
		this.numbering = numbering;
		this.bcolorcheck = bcolorcheck;
		this.bcolor = bcolor;
		this.fcolorcheck = fcolorcheck;
		this.fcolor = fcolor;
		this.materialName = materialName;
		this.masterFirstName = masterFirstName;
		this.masterSubName = masterSubName;
	}

	public int getNoofslide() {
		return noofslide;
	}

	public void setNoofslide(int noofslide) {
		this.noofslide = noofslide;
	}

	public int getNoofword() {
		return noofword;
	}

	public void setNoofword(int noofword) {
		this.noofword = noofword;
	}

	public int getBullert() {
		return bullert;
	}

	public void setBullert(int bullert) {
		this.bullert = bullert;
	}

	public int getCaudiance() {
		return caudiance;
	}

	public void setCaudiance(int caudiance) {
		this.caudiance = caudiance;
	}

	public String getFontfamily() {
		return fontfamily;
	}

	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}

	public String getPosition() {
		return position;
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

	public String getHeadertext() {
		return headertext;
	}

	public void setHeadertext(String headertext) {
		this.headertext = headertext;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getFootertext() {
		return footertext;
	}

	public void setFootertext(String footertext) {
		this.footertext = footertext;
	}

	public String getNumbering() {
		return numbering;
	}

	public void setNumbering(String numbering) {
		this.numbering = numbering;
	}

	public String getBcolorcheck() {
		return bcolorcheck;
	}

	public void setBcolorcheck(String bcolorcheck) {
		this.bcolorcheck = bcolorcheck;
	}

	public String getBcolor() {
		return bcolor;
	}

	public void setBcolor(String bcolor) {
		this.bcolor = bcolor;
	}

	public String getFcolorcheck() {
		return fcolorcheck;
	}

	public void setFcolorcheck(String fcolorcheck) {
		this.fcolorcheck = fcolorcheck;
	}

	public String getFcolor() {
		return fcolor;
	}

	public void setFcolor(String fcolor) {
		this.fcolor = fcolor;
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
	
	
	
}
