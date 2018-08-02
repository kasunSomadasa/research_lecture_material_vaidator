package com.research.slide.MaterialValidator.Dao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.font.NumericShaper.Range;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.poi.POIOLE2TextExtractor;
import org.apache.poi.hslf.model.HeadersFooters;
import org.apache.poi.hslf.usermodel.HSLFBackground;
import org.apache.poi.hslf.usermodel.HSLFFill;
import org.apache.poi.hslf.usermodel.HSLFGroupShape;
import org.apache.poi.hslf.usermodel.HSLFMasterSheet;
import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideMaster;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.CHPBinTable;
import org.apache.poi.hwpf.model.CHPX;
import org.apache.poi.hwpf.model.PAPBinTable;
import org.apache.poi.hwpf.model.PAPX;
import org.apache.poi.hwpf.model.TextPiece;
import org.apache.poi.hwpf.model.TextPieceTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.ColorStyle;
import org.apache.poi.sl.usermodel.PaintStyle;
import org.apache.poi.sl.usermodel.PaintStyle.SolidPaint;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import com.research.slide.MaterialValidator.Entity.CustomDoc;
import com.research.slide.MaterialValidator.Entity.DocReport;
import com.research.slide.MaterialValidator.Entity.Slide;
import com.research.slide.MaterialValidator.Entity.SlideCustom;
import com.research.slide.MaterialValidator.Entity.SlideReport;
import com.research.slide.MaterialValidator.Entity.SlideStandard;
import com.research.slide.MaterialValidator.Entity.SlideTemplate;
import com.research.slide.MaterialValidator.Entity.StandardDoc;

@Repository
@CrossOrigin(origins = "*")
public class SlideDao {

	private static Map<Integer, Slide> slide;
	private static Map<Integer, SlideStandard> slideStand;
	private static Map<Integer, SlideCustom> slideCustom;
	private static Map<Integer, SlideReport> slideReport;

	private static Map<Integer, CustomDoc> docCustom;
	private static Map<Integer, StandardDoc> docStand;
	private static Map<Integer, DocReport> docReport;

	int flag = 0;
	DecimalFormat numberFormat = new DecimalFormat("#.00");

	private final static Path rootLocationSlideImage = Paths.get("slide-image-dir");
	private static String[] sanSarifFont = { "Agency FB", "Arial", "Bauhaus", "Benguiat Gothic", "Berlin Sans",
			"Calibri", "Century Gothic", "Comic Sans", "Corbel", "Eras", "Franklin Gothic", "Gill Sans",
			"Haettenschweiler", "Impact", "Luci", "da Sans", "Lucida Sans Unicode", "Modern", "MS Sans Serif",
			"Segoe UI", "Tahoma", "Trebuchet MS", "Twentieth Century", "Verdana" };

	static {
		slideStand = new HashMap<Integer, SlideStandard>();
		slideCustom = new HashMap<Integer, SlideCustom>();
		slideReport = new HashMap<Integer, SlideReport>();
		docCustom = new HashMap<Integer, CustomDoc>();
		docStand = new HashMap<Integer, StandardDoc>();
		docReport = new HashMap<Integer, DocReport>();

		slide = new HashMap<Integer, Slide>() {
			{
				put(1, new Slide(1, "Slide 111111"));
				put(2, new Slide(2, "Slide 222222"));
			}
		};

	}

	public Collection<Slide> getAllSlides() {
		return this.slide.values();
	}

	public Slide getSlideByID(int id) {
		return this.slide.get(id);
	}

	public void insertSlide(Slide slide1) {

		this.slide.put(slide1.getId(), slide1);
	}

	private final Path rootLocation = Paths.get("upload-dir");

	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	private final Path rootLocationMaster = Paths.get("upload-master-dir");

	public void storeMasterSlide(MultipartFile file) {

		try {
			Files.copy(file.getInputStream(), this.rootLocationMaster.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}

	}

	public void insertStandardSlide(SlideStandard slide2) {
		this.slideStand.put(1, slide2);

		System.out.println(slide2.getMasterFirstName());
		System.out.println(slide2.getMasterSubName());

	}

	public Collection<SlideReport> validateStandardSlide() {

		this.slideReport.clear();
		try {

			if (slideStand.get(1).getMasterFirstName().endsWith("ppt")) {
				readStandradPPT();
			} else {
				readXMLStandradPPT();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.slideReport.values();

	}

	public void readStandradPPT() throws Exception {

		float total = 0;
		int x = 1;
		convertToImage(Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(), 0);
		convertToImage(Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(), 1);
		double[] rgb1 = imageProcessing(0);
		double[] rgb2 = imageProcessing(1);

		FileInputStream fis = new FileInputStream(rootLocation + "/" + slideStand.get(1).getMaterialName());
		HSLFSlideShow ppt = new HSLFSlideShow(fis);
		SlideReport reportItem;

		for (HSLFSlide slide : ppt.getSlides()) {
			/*
			 * System.out.println("Follow Master Background "+slide.
			 * getFollowMasterBackground());
			 * System.out.println("Follow Master object "+slide.
			 * getFollowMasterColourScheme());
			 * System.out.println("Follow Master object "+slide.
			 * getFollowMasterObjects());
			 * System.out.println("Follow Master Sheme "+slide.
			 * getFollowMasterScheme());
			 * 
			 * System.out.println("slide fill back color "+slide.getBackground()
			 * .getFill().getBackgroundColor());
			 * System.out.println("slide fill type "+slide.getBackground().
			 * getFill().getFillType());
			 * System.out.println("slide fill style "+slide.getBackground().
			 * getFill().getFillStyle());
			 * System.out.println("slide  pic "+slide.getBackground().getFill().
			 * getPictureData());
			 * System.out.println("slide maseter fill type "+slide.
			 * getMasterSheet().getBackground().getFill().getFillType());
			 * System.out.println("slide maseter pic "+slide.getMasterSheet().
			 * getBackground().getFill().getPictureData());
			 * System.out.println("slide maseter fill style "+slide.
			 * getMasterSheet().getBackground().getFill().getFillStyle());
			 */

			reportItem = new SlideReport();

			HeadersFooters hf = slide.getHeadersFooters();

			if (hf.isFooterVisible()) {
				reportItem.setFooter("Footer is Available");
				flag++;

			} else {
				reportItem.setFooter("Footer is not Available");
			}

			if (hf.isHeaderVisible()) {
				reportItem.setHeader("Header is Available");
				// flag++;

			} else {
				reportItem.setHeader("Header is not Available");
			}

			if (hf.isSlideNumberVisible()) {
				reportItem.setPageNumber("Slide Numbering is Available");
				flag++;

			} else {
				reportItem.setPageNumber("Slide Numbering  is not Available");
			}

			reportItem.setFontFamily(getFontFamily(slide));

			if (x == 1) {

				if (getDifferentiation(slide, slideStand.get(1).getMasterFirstName(), 0)) {

					if (getFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]));
					}

				} else {
					convertToImage(Paths.get("upload-dir") + "/" + slideStand.get(1).getMaterialName(),
							slide.getSlideNumber() - 1);
					double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

					if (getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
					}

				}
				System.out.println("flag visible" + flag);
			} else {

				if (getDifferentiation(slide, slideStand.get(1).getMasterFirstName(), 1)) {

					if (getFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]));
					}

				} else {
					convertToImage(Paths.get("upload-dir") + "/" + slideStand.get(1).getMaterialName(),
							slide.getSlideNumber() - 1);
					double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

					if (getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
					}
				}

			}

			reportItem.setSlideNo(x);
			reportItem.setFontSize(getFontSize(slide));

			reportItem.setPosition("TextBox position is " + getTextBoxPosition(slide, "left"));

			if (ppt.getSlides().size() <= 28) {
				reportItem.setMaxSlideNo("Number of slides : " + ppt.getSlides().size());
				flag++;

			} else {
				reportItem.setMaxSlideNo("Number of Slides are " + ppt.getSlides().size()
						+ ". Try to maintain number of slide less than 28");
			}

			if (getNoOfWords(slide) <= 40) {
				reportItem.setWords("Number of words per slide is good");
				flag++;

			} else {
				reportItem.setWords("Number of words per slide is " + getNoOfWords(slide)
						+ ". Try to maintain number of word per slide less than 40");
			}

			if (getNoOfBullers(slide) <= 6) {
				reportItem.setBullters("Number of bullerts per slide is good");
				flag++;

			} else {
				reportItem.setBullters("Number of bullerts per slide is " + getNoOfBullers(slide)
						+ ". Try to maintain number of bullerts per slide less than 7");
				// reportItem.setBullters("Number of bullerts per slide is . Try
				// to maintain number of bullerts per slide less than 7");

			}

			if (x == 1) {
				if (checkMasterTempText(0,
						Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(),
						rootLocation + "/" + slideStand.get(1).getMaterialName())
						&& getPicPosition(slide, slideStand.get(1).getMasterFirstName(), 0)) {
					reportItem.setPicPositions("Slide is match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not Match with logo and images in Master slide");
				}

			} else {
				if (checkMasterTempText(1,
						Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(),
						rootLocation + "/" + slideStand.get(1).getMaterialName())
						&& getPicPosition(slide, slideStand.get(1).getMasterFirstName(), 1)) {
					reportItem.setPicPositions("Slide is match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not Match with logo and images in Master slide");
				}

			}

			reportItem.setHeadertext("choose custom report");
			reportItem.setFootertext("choose custom report");
			if (flag == 10) {
				reportItem.setFlag("true");
			} else {
				reportItem.setFlag("false");
			}

			System.out.println("flag " + flag);
			reportItem.setCheckingPoints(flag);

			float divide = (float) ((float) flag / (float) 10) * 100;
			total = total + divide;

			if (x == ppt.getSlides().size()) {
				reportItem.setSummary(numberFormat.format(total / ppt.getSlides().size()));
			} else {
				reportItem.setSummary(numberFormat.format(divide));
			}
			this.slideReport.put(x, reportItem);

			flag = 0;
			x++;

			/*
			 * System.out.println("slide color "+slide.getBackground().getFill()
			 * .getBackgroundColor()); System.out.println(
			 * "-------------------------------------------------");
			 * 
			 * List<HSLFShape> shapes = slide.getShapes();
			 * 
			 * for (HSLFShape shape: shapes) { if (shape instanceof
			 * HSLFTextShape) {
			 * 
			 * HSLFTextShape textShape = (HSLFTextShape)shape;
			 * System.out.println(
			 * "shape fill color : "+textShape.getFillColor());
			 * System.out.println( "shape fill text : "+textShape.getText());
			 * System.out.println( "shape fill text : "+textShape.getText());
			 * System.out.println(
			 * "shape place X : "+textShape.getAnchor().getX());
			 * System.out.println(
			 * "shape place Y : "+textShape.getAnchor().getY());
			 * 
			 * 
			 * List<HSLFTextParagraph> texts = textShape.getTextParagraphs();
			 * for (HSLFTextParagraph text:texts){
			 * System.out.println("Text BulletFont : " + text.isBullet()); //
			 * System.out.println("Text Align : " + text.getTextAlign());
			 * System.out.println("Text BulletFont : " + text.getBulletChar());
			 * 
			 * 
			 * for (HSLFTextRun run1:text){ //XSLFTextRun run =
			 * text.addNewTextRun(); // PaintStyle.SolidPaint c = (SolidPaint)
			 * run1.getFontColor();
			 * 
			 * System.out.println("Text size: " + run1.getFontSize());
			 * System.out.println("Text family: " + run1.getFontFamily()); //
			 * System.out.println("Text color: " +
			 * c.getSolidColor().getColor());
			 * 
			 * }
			 * 
			 * } System.out.println(
			 * "-------------------------------------------------");
			 * 
			 * }
			 * 
			 * }
			 */
		}

	}

	public String getFontVisibility(HSLFSlide slide, double bblue, double bgreen, double bred) {

		List<HSLFShape> shapes = slide.getShapes();

		double backCalc = 0.0;
		String fontVisible = "";
		int fontRed, fontGreen, fontBlue = 0;

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				/*
				 * System.out.println(
				 * "shape fill color : "+textShape.getFillColor());
				 * System.out.println(
				 * "shape fill color text : "+textShape.getRawText());
				 */

				if (textShape.getFillColor() != null) {
					backCalc = ((textShape.getFillColor().getRed() * 299) + (textShape.getFillColor().getGreen() * 587)
							+ (textShape.getFillColor().getBlue() * 114)) / 1000;
				} else {
					backCalc = ((bred * 299) + (bgreen * 587) + (bblue * 114)) / 1000;
				}

				for (HSLFTextParagraph text : texts) {

					for (HSLFTextRun run1 : text) {
						if (!(run1.getRawText().equals("")) && !(run1.getRawText().equals(" "))
								&& !(run1.getRawText().trim().equals("*"))) {
							fontBlue = run1.getFontColor().getSolidColor().getColor().getBlue();
							fontGreen = run1.getFontColor().getSolidColor().getColor().getGreen();
							fontRed = run1.getFontColor().getSolidColor().getColor().getRed();

							/*
							 * System.out.println( "fontBlue "+fontBlue);
							 * System.out.println( "fontGreen "+fontGreen);
							 * System.out.println( "fontRed "+fontRed);
							 * System.out.println( "bred "+bred);
							 * System.out.println( "bgreen "+bgreen);
							 * System.out.println( "bblue "+bblue);
							 */

							double fontCalc = ((fontRed * 299) + (fontGreen * 587) + (fontBlue * 114)) / 1000;

							if (((backCalc - fontCalc) < 125) && ((backCalc - fontCalc) > -125)) {
								fontVisible = run1.getRawText() + "," + fontVisible;
							}
						}
					}
				}
			}
		}

		return fontVisible;
	}

	public String getFontFamily(HSLFSlide slide) throws IOException {

		List<HSLFShape> shapes = slide.getShapes();

		String font = "";

		String family = getFamily();

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {
						if (family.equals("")) {
							for (String f : sanSarifFont) {
								if (run1.getFontFamily().equalsIgnoreCase(f)) {

									font = "You are used recommandard San-Serif font";

									break;
								} else {

									font = "You are used non recommandard font called " + run1.getFontFamily()
											+ " for some parts. Please use san-sarif font to better readerbility";
								}
							}
						} else {
							if (run1.getFontFamily().equalsIgnoreCase(family)) {

								font = "You are used recommandard San-Serif font";

							} else {

								font = "You are used non recommandard font called " + run1.getFontFamily()
										+ " for some parts. Please use san-sarif font to better readerbility";
							}
						}
					}
				}
			}
		}
		if (font.equals("You are used recommandard San-Serif font")) {
			flag++;
		}

		return font;
	}

	public String getFamily() throws IOException {

		String value = "";
		FileInputStream mfis = new FileInputStream(
				Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName());
		HSLFSlideShow mppt = new HSLFSlideShow(mfis);
		List<HSLFSlide> masterSlide = mppt.getSlides();

		HSLFSlide shapes = masterSlide.get(0);

		for (List<HSLFTextParagraph> text : shapes.getTextParagraphs()) {
			for (HSLFTextParagraph run : text) {
				for (HSLFTextRun run1 : run) {

					if (!run1.getRawText().trim().equals(""))
						value = run1.getFontFamily();

				}
			}
		}
		return value;
	}

	public ArrayList<String> getMasterTempText(int value, String path) throws IOException {
		ArrayList<String> masterTempText = new ArrayList<String>();

		FileInputStream mfis = new FileInputStream(path);
		HSLFSlideShow mppt = new HSLFSlideShow(mfis);
		List<HSLFSlideMaster> masterSlide = mppt.getSlideMasters();

		HSLFSlideMaster shapes = masterSlide.get(0);// have to fix 0 should be
													// value

		for (List<HSLFTextParagraph> text : shapes.getTextParagraphs()) {
			for (HSLFTextParagraph run : text) {
				for (HSLFTextRun run1 : run) {

					if (!run1.getRawText().trim().equals("") && !run1.getRawText().trim().equals("*")) {

						masterTempText.add(run1.getRawText());
						// System.out.println(run1.getRawText());

					}

				}
			}
		}

		return masterTempText;
	}

	public boolean checkMasterTempText(int value, String masterPath, String slidePath) throws IOException {

		boolean test = false;
		ArrayList<String> masterText = new ArrayList<String>();
		ArrayList<String> slideText = new ArrayList<String>();

		masterText = getMasterTempText(value, masterPath);
		slideText = getMasterTempText(value, slidePath);

		if (masterText.size() == slideText.size()) {
			for (int x = 0; x < masterText.size(); x++) {
				if (masterText.get(x).charAt(0) != '<') {
					if (masterText.get(x).equals(slideText.get(x))) {
						test = true;
					} else {
						test = false;
						break;
					}
				}
			}
		}
		return test;
	}

	public boolean getPicPosition(HSLFSlide slide, String path, int number) throws IOException {
		// changed
		FileInputStream mfis = new FileInputStream(rootLocationMaster + "/" + path);
		HSLFSlideShow mppt = new HSLFSlideShow(mfis);
		HSLFSlide masterSlide = mppt.getSlides().get(number);

		List<HSLFShape> shapes = slide.getMasterSheet().getShapes();
		List<HSLFShape> masterShapes = masterSlide.getMasterSheet().getShapes();

		boolean value = false;

		for (HSLFShape mshape : masterShapes) {
			if (mshape instanceof HSLFPictureShape) {

				HSLFPictureShape mpicShape = (HSLFPictureShape) mshape;

				double mHeght = mpicShape.getPictureData().getImageDimension().getHeight();
				double mWidth = mpicShape.getPictureData().getImageDimension().getWidth();
				double mX = mpicShape.getAnchor().getX();
				double mY = mpicShape.getAnchor().getY();

				for (HSLFShape shape : shapes) {
					if (shape instanceof HSLFPictureShape) {

						HSLFPictureShape picShape = (HSLFPictureShape) shape;

						double heght = picShape.getPictureData().getImageDimension().getHeight();
						double width = picShape.getPictureData().getImageDimension().getWidth();
						double x = picShape.getAnchor().getX();
						double y = picShape.getAnchor().getY();

						if ((mHeght == heght) && (mWidth == width) && (mX == x) && (mY == y)) {
							value = true;
						}

					}
				}
				if (!value) {
					return false;
				}

			}
		}

		return value;
	}

	public int getNoOfWords(HSLFSlide slide) {

		List<HSLFShape> shapes = slide.getShapes();

		int word = 0;

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {
						if (!run1.getRawText().equals("")) {
							for (String x : run1.getRawText().split(" ")) {
								if (!x.equals("") || !x.equals(" "))
									word++;

							}

						}

					}
				}
			}
		}

		return word;
	}

	public int getNoOfBullers(HSLFSlide slide) {

		List<HSLFShape> shapes = slide.getShapes();

		int bullert = 0;

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {

					if (text.isBullet()) {

						for (HSLFTextRun run1 : text) {
							if (!(run1.getRawText().trim().equals(""))) {
								if (!(run1.getRawText().trim().charAt(0) == ' ')) {
									// System.out.println(run1.getRawText());
									bullert++;
									break;
								}

							}
						}

					}
				}
			}
		}

		return bullert;
	}

	public String getTextBoxPosition(HSLFSlide slide, String position) {

		List<HSLFShape> shapes = slide.getShapes();

		String notice = "";

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;

				if (position.equals("left")) {

					if (textShape.getAnchor().getX() < 80) {
						notice = "Good";

					} else {
						if (!textShape.getRawText().equals(""))
							notice = "Poor.Place your all textboxes in left side to get better attention";
						break;
					}

				} else if (position.equals("right")) {

					if (textShape.getAnchor().getX() > 400) {
						notice = "Good";
					} else {
						if (!textShape.getRawText().equals(""))
							notice = "Poor.Place your all textboxes in right side to get better attention";
						break;
					}
				} else {
					if (textShape.getAnchor().getX() < 400 && textShape.getAnchor().getX() > 200) {
						notice = "Good";
					} else {
						if (!textShape.getRawText().equals(""))
							notice = "Poor.Place your all textboxes in center to get better attention";
						break;

					}

				}
			}
		}

		if (notice.equals("Good")) {
			flag++;
		}
		return notice;
	}

	public boolean getDifferentiation(HSLFSlide slide, String path, int number) throws IOException {

		boolean value = false;

		FileInputStream mfis = new FileInputStream(rootLocationMaster + "/" + path);
		HSLFSlideShow mppt = new HSLFSlideShow(mfis);
		HSLFSlide masterSlide = mppt.getSlides().get(number);

		int slide_filltype = slide.getBackground().getFill().getFillType();
		int master_filltype = masterSlide.getMasterSheet().getBackground().getFill().getFillType();

		if (slide_filltype == 3 && master_filltype == 3) {
			// if(slide.getBackground().getFill().getPictureData().toString().equals(masterSlide.getMasterSheet().getBackground().getFill().getPictureData().toString()))
			value = true;
		}

		System.out.println("DIf " + value);
		return value;
	}

	public String getFontSize(HSLFSlide slide) {

		List<HSLFShape> shapes = slide.getShapes();

		double fontSize = 0;
		String notice = "";

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {
						fontSize = run1.getFontSize();

						if (slideStand.get(1).getAudianceSize() > 200) {
							if (fontSize == 42 || fontSize == 36) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 42 for Title and point 36 for Content";
								break;
							}
						} else if (200 > slideStand.get(1).getAudianceSize()
								&& slideStand.get(1).getAudianceSize() > 50) {
							if (fontSize == 36 || fontSize == 28) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 36 for Title and point 28 for Content";
								break;
							}
						} else {
							if (fontSize == 32 || fontSize == 24) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 32 for Title and point 24 for Content";
								break;
							}
						}

					}
				}
			}
		}

		if (notice.equals("you have used proper font Size")) {
			flag++;
		}
		return notice;
	}

	public static double[] imageProcessing(int number) {

		nu.pattern.OpenCV.loadShared();
		Imgcodecs imageCodecs = new Imgcodecs();
		Mat img = imageCodecs.imread(rootLocationSlideImage + "/" + "ppt_image" + number + ".png");
		int k = 1;
		Mat clusters = cluster(img, k).get(0);
		double[] rgb = clusters.get(0, 0);
		// imageCodecs.imwrite("D:/ppt_image2.png",clusters);
		return rgb;
	}

	public static List<Mat> cluster(Mat cutout, int k) {
		Mat samples = cutout.reshape(1, cutout.cols() * cutout.rows());
		Mat samples32f = new Mat();
		samples.convertTo(samples32f, CvType.CV_32F, 1.0 / 255.0);
		Mat labels = new Mat();
		TermCriteria criteria = new TermCriteria(TermCriteria.COUNT, 100, 1);
		Mat centers = new Mat();
		Core.kmeans(samples32f, k, labels, criteria, 1, Core.KMEANS_PP_CENTERS, centers);
		return showClusters(cutout, labels, centers);
	}

	private static List<Mat> showClusters(Mat cutout, Mat labels, Mat centers) {
		centers.convertTo(centers, CvType.CV_8UC1, 255.0);
		centers.reshape(3);
		List<Mat> clusters = new ArrayList<Mat>();
		for (int i = 0; i < centers.rows(); i++) {
			clusters.add(Mat.zeros(cutout.size(), cutout.type()));
		}
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i = 0; i < centers.rows(); i++)
			counts.put(i, 0);
		int rows = 0;
		for (int y = 0; y < cutout.rows(); y++) {
			for (int x = 0; x < cutout.cols(); x++) {
				int label = (int) labels.get(rows, 0)[0];
				int r = (int) centers.get(label, 2)[0];
				int g = (int) centers.get(label, 1)[0];
				int b = (int) centers.get(label, 0)[0];
				clusters.get(label).put(y, x, b, g, r);
				rows++;
			}
		}
		return clusters;
	}

	public static void convertToImage(String path, int slideNo) {

		BufferedImage img = null;
		// File file = new File(Paths.get("upload-dir")+"/"+path);
		File file = new File(path);
		HSLFSlideShow ppt;
		int x = 0;
		try {
			ppt = new HSLFSlideShow(new FileInputStream(file));
			// getting the dimensions and size of the slide
			Dimension pgsize = ppt.getPageSize();

			// System.out.println("slide no:"+slideNo);
			HSLFSlide i = ppt.getSlides().get(slideNo);
			img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = img.createGraphics();

			// clear the drawing area
			graphics.setPaint(Color.white);
			graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

			// render
			i.draw(graphics);

			// creating an image file as output
			FileOutputStream out = new FileOutputStream(rootLocationSlideImage + "/" + "ppt_image" + slideNo + ".png");
			javax.imageio.ImageIO.write(img, "png", out);
			// ppt.write(out);
			// x++;
			System.out.println("Image successfully created");
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertCustomSlide(SlideCustom slide2) {
		this.slideCustom.put(1, slide2);
	}

	public Collection<SlideReport> validateCustomSlide() {
		this.slideReport.clear();
		try {

			if (slideCustom.get(1).getMasterFirstName().endsWith("ppt")) {
				readCustomPPT();
			} else {
				readXMLCustomPPT();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.slideReport.values();
	}

	public void readCustomPPT() throws Exception {

		float total = 0;
		int x = 1;
		convertToImage(Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(), 0);
		convertToImage(Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(), 1);
		double[] rgb1 = imageProcessing(0);
		double[] rgb2 = imageProcessing(1);

		FileInputStream fis = new FileInputStream(rootLocation + "/" + slideCustom.get(1).getMaterialName());
		HSLFSlideShow ppt = new HSLFSlideShow(fis);

		for (HSLFSlide slide : ppt.getSlides()) {

			SlideReport reportItem = new SlideReport();
			HeadersFooters hf = slide.getHeadersFooters();

			if (!slideCustom.get(1).getFootertext().equals("null")) {

				if (hf.isFooterVisible()) {
					reportItem.setFooter("Footer is Available");
					flag++;
					if (hf.getFooterText().equals(slideCustom.get(1).getFootertext())) {
						reportItem.setFootertext("Footer is matching");
						flag++;
					} else {
						reportItem.setFootertext("Footer is not matching");
					}

				} else {
					reportItem.setFooter("Footer is not Available");
					reportItem.setFootertext("Footer is not Available");
				}

			} else {
				reportItem.setFooter("Footer feature is not enabled");
				reportItem.setFootertext("Footer feature is not enabled");
			}

			if (!slideCustom.get(1).getHeadertext().equals("null")) {

				if (hf.isHeaderVisible()) {
					reportItem.setHeader("Header is Available");
					// flag++;
					if (hf.getHeaderText().equals(slideCustom.get(1).getHeadertext())) {
						reportItem.setHeadertext("Header is matching");
						// flag++;
					} else {
						reportItem.setHeadertext("Header is not matching");
					}
				} else {
					reportItem.setHeader("Header is not Available");
					reportItem.setHeadertext("Header is not Available");
				}

			} else {
				reportItem.setHeader("Header feature is not enabled");
				reportItem.setHeadertext("Header feature is not enabled");
			}

			if (slideCustom.get(1).getNumbering().equals("on")) {

				if (hf.isSlideNumberVisible()) {
					reportItem.setPageNumber("Slide Numbering is Available");
					flag++;
				} else {
					reportItem.setPageNumber("Slide Numbering  is not Available");
				}

			} else {
				reportItem.setPageNumber("Numbering feature is not enabled");
			}

			reportItem.setFontFamily(getCustomFontFamily(slide));

			if (!slideCustom.get(1).getBcolor().equals("null") && !slideCustom.get(1).getFcolor().equals("null")) {

				Color bc = hex2Rgb(slideCustom.get(1).getBcolor());
				Color fc = hex2Rgb(slideCustom.get(1).getFcolor());
				reportItem.setContrast(getFontCustomVisibility(slide, bc.getRed(), bc.getBlue(), bc.getGreen(),
						fc.getRed(), fc.getBlue(), fc.getGreen()));

			} else {

				if (x == 1) {

					if (getDifferentiation(slide, slideCustom.get(1).getMasterFirstName(), 0)) {

						if (getFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]));
						}

					} else {
						convertToImage(Paths.get("upload-dir") + "/" + slideCustom.get(1).getMaterialName(),
								slide.getSlideNumber() - 1);
						double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

						if (getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
						}
					}

				} else {

					if (getDifferentiation(slide, slideCustom.get(1).getMasterFirstName(), 1)) {

						if (getFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]));
						}

					} else {
						convertToImage(Paths.get("upload-dir") + "/" + slideCustom.get(1).getMaterialName(),
								slide.getSlideNumber() - 1);
						double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

						if (getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
						}
					}
				}
			}

			reportItem.setSlideNo(x);

			if (slideCustom.get(1).getNoofword() > 36) {
				reportItem.setFontSize("Can't calculate font size, Because you have exceeded recommended no of words");
			} else if (slideCustom.get(1).getBullert() > 6) {
				reportItem
						.setFontSize("Can't calculate font size, Because you have exceeded recommended no of bullets");
			} else {
				reportItem.setFontSize(getCustomFontSize(slide));
			}

			reportItem
					.setPosition("TextBox position is " + getTextBoxPosition(slide, slideCustom.get(1).getPosition()));

			if (ppt.getSlides().size() <= slideCustom.get(1).getNoofslide()) {
				reportItem.setMaxSlideNo("Number of slides are " + ppt.getSlides().size());
				flag++;
			} else {
				reportItem.setMaxSlideNo("Number of slides are " + ppt.getSlides().size()
						+ ". Try to maintain number of slide less than " + slideCustom.get(1).getNoofslide());
			}

			if (getNoOfWords(slide) <= slideCustom.get(1).getNoofword()) {
				reportItem.setWords("Number of words per slide is good");
				flag++;
			} else {
				reportItem.setWords("Number of words per slide is " + getNoOfWords(slide)
						+ ". Try to maintain number of words per slide less than " + slideCustom.get(1).getNoofword());
			}
			if (getNoOfBullers(slide) <= slideCustom.get(1).getBullert()) {
				reportItem.setBullters("Number of bullerts per slide is good");
				flag++;
			} else {
				reportItem.setBullters("Number of bullerts per slide is " + getNoOfBullers(slide)
						+ ". Try to maintain number of bullerts per slide less than "
						+ slideCustom.get(1).getBullert());
			}

			if (x == 1) {
				if (checkMasterTempText(0,
						Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(),
						rootLocation + "/" + slideCustom.get(1).getMaterialName())
						&& getPicPosition(slide, slideCustom.get(1).getMasterFirstName(), 0)) {
					reportItem.setPicPositions("Slide match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not match with logo and images in Master slide");
				}
			} else {
				if (checkMasterTempText(1,
						Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(),
						rootLocation + "/" + slideCustom.get(1).getMaterialName())
						&& getPicPosition(slide, slideCustom.get(1).getMasterFirstName(), 1)) {
					reportItem.setPicPositions("Slide match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not match with logo and images in Master slide");
				}
			}

			if (flag == 10) {// 13
				reportItem.setFlag("true");
			} else {
				reportItem.setFlag("false");
			}

			System.out.println("flag " + flag);

			reportItem.setCheckingPoints(flag);

			float divide = (float) ((float) flag / (float) 10) * 100;
			System.out.println("divide " + divide);

			total = total + divide;

			System.out.println(numberFormat.format(total / ppt.getSlides().size()));
			if (x == ppt.getSlides().size()) {
				reportItem.setSummary(numberFormat.format(total / ppt.getSlides().size()));
			} else {
				reportItem.setSummary(numberFormat.format(divide));
			}

			this.slideReport.put(x, reportItem);
			flag = 0;
			x++;

		}
	}

	private String getFontCustomVisibility(HSLFSlide slide, double bred, double bblue, double bgreen, double fred,
			double fblue, double fgreen) {

		List<HSLFShape> shapes = slide.getShapes();

		String infor, fcolorinfo = "";
		String font = "";
		String bcolorinfo = "";
		int fontRed, fontGreen, fontBlue, backRed, backGreen, backBlue = 0;

		backBlue = slide.getBackground().getFill().getBackgroundColor().getBlue();
		backRed = slide.getBackground().getFill().getBackgroundColor().getRed();
		backGreen = slide.getBackground().getFill().getBackgroundColor().getGreen();

		if ((backBlue == bblue) && (backGreen == bgreen) && (backRed == bred)) {
			bcolorinfo = "Background match with color";
		} else {
			bcolorinfo = "Background doesn't match with color";
			;
		}

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {
						if (!(run1.getRawText().equals("")) && !(run1.getRawText().equals(" "))
								&& !(run1.getRawText().trim().equals("*"))) {
							fontBlue = run1.getFontColor().getSolidColor().getColor().getBlue();
							fontGreen = run1.getFontColor().getSolidColor().getColor().getGreen();
							fontRed = run1.getFontColor().getSolidColor().getColor().getRed();

							if ((fontBlue == fblue) && (fontGreen == fgreen) && (fontRed == fred)) {
								fcolorinfo = "Fonts match with color";
							} else {
								font = font + "," + run1.getRawText();
							}
						}
					}
				}
			}
		}

		if (fcolorinfo.equals("Fonts match with color")) {
			infor = bcolorinfo + " and " + fcolorinfo;
		} else {
			infor = bcolorinfo + " and thses fonts does not match with color (" + font + ")";
		}

		return infor;
	}

	public String getCustomFontFamily(HSLFSlide slide) {

		List<HSLFShape> shapes = slide.getShapes();
		String f = slideCustom.get(1).getFontfamily();

		String font = "";

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {

						if (run1.getFontFamily().equalsIgnoreCase(f)) {

							font = "You are used recommandard San-Serif font";
							break;
						} else {

							font = "You are used non recommandard font called " + run1.getFontFamily()
									+ " for some parts. Please use san-sarif font called " + f
									+ " to better readerbility";
						}

					}
				}
			}
		}
		if (font.equals("You are used recommandard San-Serif font")) {
			flag++;
		}

		return font;
	}

	public String getCustomFontSize(HSLFSlide slide) {

		List<HSLFShape> shapes = slide.getShapes();

		double fontSize = 0;
		String notice = "";

		for (HSLFShape shape : shapes) {
			if (shape instanceof HSLFTextShape) {

				HSLFTextShape textShape = (HSLFTextShape) shape;
				List<HSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (HSLFTextParagraph text : texts) {
					for (HSLFTextRun run1 : text) {
						fontSize = run1.getFontSize();

						if (slideCustom.get(1).getCaudiance() > 200) {
							if (fontSize == 42 || fontSize == 36) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 42 for Title and point 36 for Content";
								break;
							}
						} else if (200 > slideCustom.get(1).getCaudiance() && slideCustom.get(1).getCaudiance() > 50) {
							if (fontSize == 36 || fontSize == 28) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 36 for Title and point 28 for Content";
								break;
							}
						} else {
							if (fontSize == 32 || fontSize == 24) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 32 for Title and point 24 for Content";
								break;
							}
						}

					}
				}
			}
		}
		if (notice.equals("you have used proper font Size")) {
			flag++;
		}
		return notice;
	}

	public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	ArrayList<ParagraphAlignment> mHeaderAlignment = new ArrayList<ParagraphAlignment>();
	ArrayList<String> mHeaderText = new ArrayList<String>();
	ArrayList<Integer> mHeaderTextSize = new ArrayList<Integer>();
	ArrayList<Boolean> mHeaderTextBold = new ArrayList<Boolean>();
	ArrayList<Long> mhpicX = new ArrayList<Long>();
	ArrayList<Long> mhpicY = new ArrayList<Long>();

	ArrayList<ParagraphAlignment> headerAlignment = new ArrayList<ParagraphAlignment>();
	ArrayList<String> headerText = new ArrayList<String>();
	ArrayList<Integer> headerTextSize = new ArrayList<Integer>();
	ArrayList<Boolean> headerTextBold = new ArrayList<Boolean>();
	ArrayList<Long> hpicX = new ArrayList<Long>();
	ArrayList<Long> hpicY = new ArrayList<Long>();

	ArrayList<ParagraphAlignment> mFooterAlignment = new ArrayList<ParagraphAlignment>();
	ArrayList<String> mFooterText = new ArrayList<String>();
	ArrayList<Integer> mFooterTextSize = new ArrayList<Integer>();
	ArrayList<Boolean> mFooterTextBold = new ArrayList<Boolean>();
	ArrayList<Long> mfpicX = new ArrayList<Long>();
	ArrayList<Long> mfpicY = new ArrayList<Long>();

	ArrayList<ParagraphAlignment> footerAlignment = new ArrayList<ParagraphAlignment>();
	ArrayList<String> footerText = new ArrayList<String>();
	ArrayList<Integer> footerTextSize = new ArrayList<Integer>();
	ArrayList<Boolean> footerTextBold = new ArrayList<Boolean>();
	ArrayList<Long> fpicX = new ArrayList<Long>();
	ArrayList<Long> fpicY = new ArrayList<Long>();

	public void getDocParameters(String path) throws IOException {

		FileInputStream doc = new FileInputStream(path);
		XWPFDocument docx = new XWPFDocument(doc);

		for (XWPFHeader header : docx.getHeaderList()) {
			for (XWPFParagraph paragraph : header.getListParagraph()) {

				if (!paragraph.getParagraphText().trim().equals("")) {
					headerAlignment.add(paragraph.getAlignment());

					for (String text : paragraph.getText().split(" ")) {
						headerText.add(text);
						// System.out.println("header : "+text);
					}
				}
				for (XWPFRun run : paragraph.getRuns()) {
					if (!run.text().trim().equals("")) {
						// headerText.add(run.text());

						headerTextSize.add(run.getFontSize());

						if (run.isBold()) {
							headerTextBold.add(true);
						} else {
							headerTextBold.add(false);
						}

						break;
					}

					for (XWPFPicture pic : run.getEmbeddedPictures()) {

						hpicX.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCx());
						hpicY.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCy());
					}
				}
			}
		}

		for (XWPFFooter footer : docx.getFooterList()) {
			for (XWPFParagraph paragraph : footer.getListParagraph()) {

				if (!paragraph.getParagraphText().trim().equals("")) {
					footerAlignment.add(paragraph.getAlignment());

					for (String text : paragraph.getText().split(" ")) {
						footerText.add(text);
					}
				}
				for (XWPFRun run : paragraph.getRuns()) {
					if (!run.text().trim().equals("")) {
						// footerText.add(run.text());

						footerTextSize.add(run.getFontSize());

						if (run.isBold()) {
							footerTextBold.add(true);
						} else {
							footerTextBold.add(false);
						}

						break;
					}

					for (XWPFPicture pic : run.getEmbeddedPictures()) {

						fpicX.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCx());
						fpicY.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCy());
					}
				}
			}
		}
	}

	public void getMasterDocParameters(String path) throws IOException {

		FileInputStream mdoc = new FileInputStream(path);
		XWPFDocument docx = new XWPFDocument(mdoc);

		for (XWPFHeader header : docx.getHeaderList()) {
			for (XWPFParagraph paragraph : header.getListParagraph()) {

				if (!paragraph.getParagraphText().trim().equals("")) {
					mHeaderAlignment.add(paragraph.getAlignment());

					for (String text : paragraph.getText().split(" ")) {
						mHeaderText.add(text);
						// System.out.println("mheader : "+text);
					}
				}
				for (XWPFRun run : paragraph.getRuns()) {
					if (!run.text().trim().equals("") && !run.text().trim().equals("<")
							&& !run.text().trim().equals(">")) {
						// mHeaderText.add(run.text());

						mHeaderTextSize.add(run.getFontSize());

						if (run.isBold()) {
							mHeaderTextBold.add(true);
						} else {
							mHeaderTextBold.add(false);
						}

						break;
					}

					for (XWPFPicture pic : run.getEmbeddedPictures()) {

						mhpicX.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCx());
						mhpicY.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCy());
					}
				}
			}
		}

		for (XWPFFooter footer : docx.getFooterList()) {
			for (XWPFParagraph paragraph : footer.getListParagraph()) {

				if (!paragraph.getParagraphText().trim().equals("")) {
					mFooterAlignment.add(paragraph.getAlignment());

					for (String text : paragraph.getText().split(" ")) {
						mFooterText.add(text);
					}
				}
				for (XWPFRun run : paragraph.getRuns()) {
					if (!run.text().trim().equals("") && !run.text().trim().equals("<")
							&& !run.text().trim().equals(">")) {
						// mFooterText.add(run.text());

						mFooterTextSize.add(run.getFontSize());

						if (run.isBold()) {
							mFooterTextBold.add(true);
						} else {
							mFooterTextBold.add(false);
						}

						break;
					}

					for (XWPFPicture pic : run.getEmbeddedPictures()) {

						mfpicX.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCx());
						mfpicY.add(pic.getCTPicture().getSpPr().getXfrm().getExt().getCy());
					}
				}
			}
		}
	}

	public boolean docAlignmentCheck() {

		boolean value = true;
		if (mHeaderAlignment.size() != headerAlignment.size()) {
			value = false;
		} else {
			for (int x = 0; x < mHeaderAlignment.size(); x++) {
				if (!mHeaderAlignment.get(x).equals(headerAlignment.get(x))) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docHeaderTextSize() {

		boolean value = true;
		if (mHeaderTextSize.size() != headerTextSize.size()) {
			value = false;
		} else {
			for (int x = 0; x < mHeaderTextSize.size(); x++) {
				if (mHeaderTextSize.get(x) != headerTextSize.get(x)) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docHeaderTextBold() {

		boolean value = true;
		if (mHeaderTextBold.size() != headerTextBold.size()) {
			value = false;
		} else {
			for (int x = 0; x < mHeaderTextBold.size(); x++) {
				if (mHeaderTextBold.get(x) != headerTextBold.get(x)) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docHeaderText() {

		boolean value = true;
		if (mHeaderText.size() != headerText.size()) {
			value = false;
		} else {
			for (int x = 0; x < mHeaderText.size(); x++) {
				int len = mHeaderText.get(x).trim().length();

				if ((mHeaderText.get(x).trim().charAt(0) != '<')
						&& (mHeaderText.get(x).trim().charAt(len - 1) != '>')) {
					if (!mHeaderText.get(x).trim().equals(headerText.get(x).trim())) {
						value = false;
						break;
					}
				}

			}
		}
		return value;
	}

	public boolean docFooterAlignmentCheck() {

		boolean value = true;
		if (mFooterAlignment.size() != footerAlignment.size()) {
			value = false;
		} else {
			for (int x = 0; x < mFooterAlignment.size(); x++) {
				if (!mFooterAlignment.get(x).equals(footerAlignment.get(x))) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docFooterTextSize() {

		boolean value = true;
		if (mFooterTextSize.size() != footerTextSize.size()) {
			value = false;
		} else {
			for (int x = 0; x < mFooterTextSize.size(); x++) {
				if (mFooterTextSize.get(x) != footerTextSize.get(x)) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docFooterTextBold() {

		boolean value = true;
		if (mFooterTextBold.size() != footerTextBold.size()) {
			value = false;
		} else {
			for (int x = 0; x < mFooterTextBold.size(); x++) {
				if (mFooterTextBold.get(x) != footerTextBold.get(x)) {
					value = false;
					break;
				}
			}
		}
		return value;
	}

	public boolean docFooterText() {

		boolean value = true;
		if (mFooterText.size() != footerText.size()) {
			value = false;
		} else {
			for (int x = 0; x < mFooterText.size(); x++) {
				int len = mFooterText.get(x).trim().length();
				if ((mFooterText.get(x).trim().charAt(0) != '<')
						&& (mFooterText.get(x).trim().charAt(len - 1) != '>')) {
					if (!mFooterText.get(x).trim().equals(footerText.get(x).trim())) {
						value = false;
						break;
					}
				}

			}
		}
		return value;
	}

	public String getMasterDocFontFamily(String path) throws IOException {

		String value = "";
		FileInputStream doc = new FileInputStream(path);
		XWPFDocument docx = new XWPFDocument(doc);

		for (XWPFParagraph paragraph : docx.getParagraphs()) {

			for (XWPFRun run : paragraph.getRuns()) {

				value = run.getFontFamily();
				if (!value.trim().equals(""))
					break;
			}
		}
		return value;
	}

	public boolean checkDocFontFamily(String path, String family) throws IOException {

		boolean value = true;
		FileInputStream doc = new FileInputStream(path);
		XWPFDocument docx = new XWPFDocument(doc);

		if (family.trim().equals("")) {

			for (String f : sanSarifFont) {

				for (XWPFParagraph paragraph : docx.getParagraphs()) {

					for (XWPFRun run : paragraph.getRuns()) {

						if (f.equals(run.getFontFamily())) {
							value = false;
							break;
						}

					}
				}
			}

		} else {

			for (XWPFParagraph paragraph : docx.getParagraphs()) {

				for (XWPFRun run : paragraph.getRuns()) {

					if (!family.equals(run.getFontFamily())) {
						value = false;
						break;
					}

				}
			}
		}
		return value;
	}

	public boolean checkTemplateHeader() {

		boolean value = true;
		if ((hpicX.size() == mhpicX.size()) && (hpicY.size() == mhpicY.size())) {

			for (int x = 0; x < hpicX.size(); x++) {

				if (!(hpicX.get(x).equals(mhpicX.get(x))) || !(hpicY.get(x).equals(mhpicY.get(x)))) {

					value = false;
					break;
				}
			}
		} else {
			value = false;
		}

		return value;
	}

	public boolean checkTemplateFooter() {

		boolean value = true;
		if ((fpicX.size() == mfpicX.size()) && (fpicY.size() == mfpicY.size())) {

			for (int x = 0; x < fpicX.size(); x++) {

				if (!(fpicX.get(x).equals(mfpicX.get(x))) || !(fpicY.get(x).equals(mfpicY.get(x)))) {

					value = false;
					break;
				}
			}
		} else {
			value = false;
		}

		return value;
	}

	public void readDoc() {
		try {

			// FileInputStream mdoc = new
			// FileInputStream(rootLocationDocMaster+"/"+docStand.get(1).getMasterFirstName());
			// FileInputStream doc = new
			// FileInputStream(rootLocation+"/"+docStand.get(1).getMaterialName());

			String mPath = rootLocationDocMaster + "\\MTute.docx";
			String path = rootLocation + "\\Tute.docx";

			getMasterDocParameters(mPath);
			getDocParameters(path);

			if (docAlignmentCheck() && docHeaderText() && docHeaderTextBold() && docHeaderTextSize()) {
				System.out.println("header is correct");
			}

			if (docFooterAlignmentCheck() && docFooterText() && docFooterTextBold() && docFooterTextSize()) {
				System.out.println("footer is correct");
			}

			String fontFamily = getMasterDocFontFamily(mPath);

			if (checkDocFontFamily(path, fontFamily)) {
				System.out.println("family is correct");
			}

			if (checkTemplateFooter() && checkTemplateHeader()) {
				System.out.println("template is correct");
			}

			/*
			 * File filex = new File("F:\\our_research\\Tutemaster.docx");
			 * FileInputStream fisx=new
			 * FileInputStream(filex.getAbsolutePath()); XWPFDocument docx = new
			 * XWPFDocument(fisx);
			 * 
			 * 
			 * for (XWPFParagraph paragraph : docx.getParagraphs()) {
			 * System.out.println(paragraph.getText()); for (XWPFRun run :
			 * paragraph.getRuns()) { System.out.println(run.getFontFamily()); }
			 * }
			 * 
			 * System.out.println("////////////////////");
			 * 
			 * 
			 * for (XWPFHeader header : docx.getHeaderList()) {
			 * 
			 * 
			 * for (XWPFParagraph paragraph : header.getListParagraph()) { //
			 * System.out.println(paragraph.getParagraphText());
			 * 
			 * 
			 * 
			 * for (XWPFRun run : paragraph.getRuns()) {
			 * 
			 * for (XWPFPicture pic : run.getEmbeddedPictures()) { //
			 * System.out.println("x : "+pic.getCTPicture().getSpPr().getXfrm().
			 * getExt().getCx()); //
			 * System.out.println("Y : "+pic.getCTPicture().getSpPr().getXfrm().
			 * getExt().getCy());
			 * 
			 * 
			 * }
			 * 
			 * }
			 * 
			 * 
			 * } }
			 * 
			 * 
			 * 
			 * 
			 * /* FileI nputStream mfis = new
			 * FileInputStream("D:\\SPM-Lecture.ppt"); HSLFSlideShow ppt = new
			 * HSLFSlideShow(new HSLFSlideShowImpl("D:\\SPM-Lecture.ppt"));
			 * 
			 * //getting the dimensions and size of the slide Dimension pgsize =
			 * ppt.getPageSize(); List<HSLFSlide> slide = ppt.getSlides();
			 * 
			 * 
			 * for (int i = 0; i < slide.size(); i++) { BufferedImage img = new
			 * BufferedImage(pgsize.width,
			 * pgsize.height,BufferedImage.TYPE_INT_RGB); Graphics2D graphics =
			 * img.createGraphics();
			 * 
			 * //clear the drawing area
			 * graphics.setPaint(slide.get(i).getMasterSheet().getBackground().
			 * getFill().getBackgroundColor());
			 * 
			 * 
			 * List<HSLFShape> shapes =
			 * slide.get(i).getMasterSheet().getShapes(); for (HSLFShape shape:
			 * shapes) {
			 * 
			 * if (shape instanceof HSLFGroupShape) {
			 * 
			 * slide.get(i).addShape(shape);
			 * 
			 * }
			 * 
			 * 
			 * } graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
			 * pgsize.height));
			 * 
			 * //render slide.get(i).draw(graphics); FileOutputStream out = new
			 * FileOutputStream("D:\\images\\"+"ppt_image"+i+".jpg");
			 * 
			 * javax.imageio.ImageIO.write(img, "jpg", out); ppt.write(out);
			 * System.out.println("Image successfully created"); out.close(); }
			 * //creating an image file as output
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * /* System.out.println(
			 * "///////////////////////////////////////////////////"); File file
			 * = new File("F:\\our_research\\Tute.doc"); FileInputStream fis=new
			 * FileInputStream(file.getAbsolutePath()); HWPFDocument
			 * document=new HWPFDocument(fis); WordExtractor extractor = new
			 * WordExtractor(document); // read the doc as rtf
			 * 
			 * HeaderStories headerStories = new HeaderStories(document);
			 * 
			 * 
			 * for (Picture p : document.getPicturesTable().getAllPictures()){
			 * // System.out.println("heghit "+p.getHeight() +
			 * " width "+p.getWidth()); }
			 * 
			 * System.out.println(extractor.getText());
			 * 
			 * if(!text.equals("")){ if(text.equals(extractor.getText())){ //
			 * System.out.println("true"); } } text=extractor.getText();
			 * 
			 * // Display the document's header and footer text //
			 * System.out.println("Footer text: " + extractor); //
			 * System.out.println("Header text: " + extractor.getHeaderText());
			 */

			/*
			 * HWPFDocument doc = new HWPFDocument(fs); WordExtractor we = new
			 * WordExtractor(doc); Range range = doc.getRange(); 
			 * String[] paragraphs = we.getParagraphText(); for (int i = 0; i <
			 * paragraphs.length; i++) { org.apache.poi.hwpf.usermodel.Paragraph
			 * pr = range.getParagraph(i);
			 * 
			 * System.out.println(pr.getEndOffset()); int j=0; while (true) {
			 * CharacterRun run = pr.getCharacterRun(j++);
			 * System.out.println("-------------------------------");
			 * System.out.println("Color---"+ run.getColor());
			 * System.out.println("getFontName---"+ run.getFontName());
			 * System.out.println("getFontSize---"+ run.getFontSize());
			 * 
			 * if( run.getEndOffset()==pr.getEndOffset()){ break; } }
			 * 
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void insertStandardDoc(StandardDoc doc) {
		this.docStand.put(1, doc);
	}

	public void insertCustomDoc(CustomDoc doc) {
		this.docCustom.put(1, doc);
	}

	private final Path rootLocationDocMaster = Paths.get("upload-doc-master-dir");

	public void storeMasterDoc(MultipartFile file) {

		try {
			Files.copy(file.getInputStream(), this.rootLocationDocMaster.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}

	}

	public Collection<DocReport> validateStandardDoc() {
		this.docReport.clear();
		try {
			
			if (docStand.get(1).getMasterFirstName().endsWith("docx")) {
				readStandardDoc();
			} else {
				readXMLStandardDoc();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.docReport.values();
	}

	public void readStandardDoc() {

		try {
			String mPath = rootLocationDocMaster + "/" + docStand.get(1).getMasterFirstName();
			String path = rootLocation + "/" + docStand.get(1).getMaterialName();
			DocReport reportItem = new DocReport();
			getMasterDocParameters(mPath);
			getDocParameters(path);

			if (docAlignmentCheck() && docHeaderText() && docHeaderTextBold() && docHeaderTextSize()) {
				System.out.println("header is correct");
				reportItem.setHeader("ok");
			} else {
				reportItem.setHeader("no");
			}

			if (docFooterAlignmentCheck() && docFooterText() && docFooterTextBold() && docFooterTextSize()) {
				System.out.println("footer is correct");
				reportItem.setFooter("ok");
			} else {
				reportItem.setFooter("no");
			}

			String fontFamily = getMasterDocFontFamily(mPath);

			if (checkDocFontFamily(path, fontFamily)) {
				System.out.println("family is correct");
				reportItem.setFamily("ok");
			} else {
				reportItem.setFamily("no");
			}

			if (checkTemplateFooter() && checkTemplateHeader()) {
				System.out.println("template is correct");
				reportItem.setTemplate("ok");
			} else {
				reportItem.setTemplate("no");
			}

			this.docReport.put(1, reportItem);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Collection<DocReport> validateCustomDoc() {
		this.docReport.clear();
		try {
			
			if (docCustom.get(1).getMasterFirstName().endsWith("docx")) {
				readCustomDoc();
			} else {
				readXMLCustomDoc();
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.docReport.values();
	}

	public void readCustomDoc() {

		try {
			String mPath = rootLocationDocMaster + "/" + docCustom.get(1).getMasterFirstName();
			String path = rootLocation + "/" + docCustom.get(1).getMaterialName();
			DocReport reportItem = new DocReport();
			getMasterDocParameters(mPath);
			getDocParameters(path);

			if (docCustom.get(1).getHeader().equals("on")) {

				if (docAlignmentCheck() && docHeaderText() && docHeaderTextBold() && docHeaderTextSize()) {
					System.out.println("header is correct");
					reportItem.setHeader("ok");
				} else {
					reportItem.setHeader("no");
				}

			} else {
				reportItem.setHeader("not available");
			}

			if (docCustom.get(1).getFooter().equals("on")) {

				if (docFooterAlignmentCheck() && docFooterText() && docFooterTextBold() && docFooterTextSize()) {
					System.out.println("footer is correct");
					reportItem.setFooter("ok");
				} else {
					reportItem.setFooter("no");
				}

			} else {
				reportItem.setFooter("not available");
			}

			if (!docCustom.get(1).getFontfamily().equals("null")) {

				String fontFamily = docCustom.get(1).getFontfamily();

				if (checkDocFontFamily(path, fontFamily)) {
					System.out.println("family is correct");
					reportItem.setFamily("ok");
				} else {
					reportItem.setFamily("no");
				}

			} else {
				String fontFamily = "";

				if (checkDocFontFamily(path, fontFamily)) {
					System.out.println("family is correct");
					reportItem.setFamily("ok");
				} else {
					reportItem.setFamily("no");
				}
			}

			if (checkTemplateFooter() && checkTemplateHeader()) {
				System.out.println("template is correct");
				reportItem.setTemplate("ok");
			} else {
				reportItem.setTemplate("no");
			}

			this.docReport.put(1, reportItem);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// pptx part
	public void readXMLStandradPPT() throws Exception {

		float total = 0;
		int x = 1;
		convertToXMLImage(Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(), 0);
		convertToXMLImage(Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(), 1);
		double[] rgb1 = imageProcessing(0);
		double[] rgb2 = imageProcessing(1);

		FileInputStream fis = new FileInputStream(rootLocation + "/" + slideStand.get(1).getMaterialName());

		SlideReport reportItem;
		XMLSlideShow pptx = new XMLSlideShow(fis);
		for (XSLFSlide slide : pptx.getSlides()) {

			/*
			 * System.out.println("Follow Master Background "+slide.
			 * getFollowMasterBackground());
			 * System.out.println("Follow Master object "+slide.
			 * getFollowMasterColourScheme());
			 * System.out.println("Follow Master object "+slide.
			 * getFollowMasterObjects());
			 * System.out.println("Follow Master Sheme "+slide.
			 * getFollowMasterScheme());
			 * 
			 * System.out.println("slide fill back color "+slide.getBackground()
			 * .getFill().getBackgroundColor());
			 * System.out.println("slide fill type "+slide.getBackground().
			 * getFill().getFillType());
			 * System.out.println("slide fill style "+slide.getBackground().
			 * getFill().getFillStyle());
			 * System.out.println("slide  pic "+slide.getBackground().getFill().
			 * getPictureData());
			 * System.out.println("slide maseter fill type "+slide.
			 * getMasterSheet().getBackground().getFill().getFillType());
			 * System.out.println("slide maseter pic "+slide.getMasterSheet().
			 * getBackground().getFill().getPictureData());
			 * System.out.println("slide maseter fill style "+slide.
			 * getMasterSheet().getBackground().getFill().getFillStyle());
			 */

			reportItem = new SlideReport();

			reportItem.setFooter("Footer feature is not available for pptx files");

			reportItem.setHeader("Header feature is not available for pptx files");

			reportItem.setPageNumber("Slide Numbering  feature is not available for pptx files");

			reportItem.setFontFamily(getXMLFontFamily(slide));

			if (x == 1) {

				if (getXMLDifferentiation(slide, slideStand.get(1).getMasterFirstName(), 0)) {

					if (getXMLFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getXMLFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]));
					}

				} else {
					convertToXMLImage(Paths.get("upload-dir") + "/" + slideStand.get(1).getMaterialName(),
							slide.getSlideNumber() - 1);
					double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

					if (getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
					}

				}
				System.out.println("flag visible" + flag);
			} else {

				if (getXMLDifferentiation(slide, slideStand.get(1).getMasterFirstName(), 1)) {

					if (getXMLFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getXMLFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]));
					}

				} else {
					convertToXMLImage(Paths.get("upload-dir") + "/" + slideStand.get(1).getMaterialName(),
							slide.getSlideNumber() - 1);
					double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

					if (getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
						reportItem.setContrast("Visibility is Good");
						flag++;
					} else {
						reportItem.setContrast("Visibility is Poor in these words : "
								+ getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
					}
				}

			}

			reportItem.setSlideNo(x);
			reportItem.setFontSize(getXMLFontSize(slide));

			reportItem.setPosition("TextBox position is " + getXMLTextBoxPosition(slide, "left"));

			if (pptx.getSlides().size() <= 28) {
				reportItem.setMaxSlideNo("Number of slides : " + pptx.getSlides().size());
				flag++;

			} else {
				reportItem.setMaxSlideNo("Number of Slides are " + pptx.getSlides().size()
						+ ". Try to maintain number of slide less than 28");
			}

			if (getXMLNoOfWords(slide) <= 40) {
				reportItem.setWords("Number of words per slide is good");
				flag++;

			} else {
				reportItem.setWords("Number of words per slide is " + getXMLNoOfWords(slide)
						+ ". Try to maintain number of word per slide less than 40");
			}

			if (getXMLNoOfBullers(slide) <= 6) {
				reportItem.setBullters("Number of bullerts per slide is good");
				flag++;

			} else {
				reportItem.setBullters("Number of bullerts per slide is " + getXMLNoOfBullers(slide)
						+ ". Try to maintain number of bullerts per slide less than 7");
				// reportItem.setBullters("Number of bullerts per slide is . Try
				// to maintain number of bullerts per slide less than 7");

			}

			if (x == 1) {
				if (checkXMLMasterTempText(0,
						Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(),
						rootLocation + "/" + slideStand.get(1).getMaterialName())
						&& getXMLPicPosition(slide, slideStand.get(1).getMasterFirstName(), 0)) {
					reportItem.setPicPositions("Slide is match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not Match with logo and images in Master slide");
				}

			} else {
				if (checkXMLMasterTempText(1,
						Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName(),
						rootLocation + "/" + slideStand.get(1).getMaterialName())
						&& getXMLPicPosition(slide, slideStand.get(1).getMasterFirstName(), 1)) {
					reportItem.setPicPositions("Slide is match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not Match with logo and images in Master slide");
				}

			}

			reportItem.setHeadertext("choose custom report");
			reportItem.setFootertext("choose custom report");
			if (flag == 10) {
				reportItem.setFlag("true");
			} else {
				reportItem.setFlag("false");
			}

			System.out.println("flag " + flag);
			reportItem.setCheckingPoints(flag);

			float divide = (float) ((float) flag / (float) 10) * 100;
			total = total + divide;

			if (x == pptx.getSlides().size()) {
				reportItem.setSummary(numberFormat.format(total / pptx.getSlides().size()));
			} else {
				reportItem.setSummary(numberFormat.format(divide));
			}
			this.slideReport.put(x, reportItem);

			flag = 0;
			x++;

			/*
			 * System.out.println("slide color "+slide.getBackground().getFill()
			 * .getBackgroundColor()); System.out.println(
			 * "-------------------------------------------------");
			 * 
			 * List<HSLFShape> shapes = slide.getShapes();
			 * 
			 * for (HSLFShape shape: shapes) { if (shape instanceof
			 * HSLFTextShape) {
			 * 
			 * HSLFTextShape textShape = (HSLFTextShape)shape;
			 * System.out.println(
			 * "shape fill color : "+textShape.getFillColor());
			 * System.out.println( "shape fill text : "+textShape.getText());
			 * System.out.println( "shape fill text : "+textShape.getText());
			 * System.out.println(
			 * "shape place X : "+textShape.getAnchor().getX());
			 * System.out.println(
			 * "shape place Y : "+textShape.getAnchor().getY());
			 * 
			 * 
			 * List<HSLFTextParagraph> texts = textShape.getTextParagraphs();
			 * for (HSLFTextParagraph text:texts){
			 * System.out.println("Text BulletFont : " + text.isBullet()); //
			 * System.out.println("Text Align : " + text.getTextAlign());
			 * System.out.println("Text BulletFont : " + text.getBulletChar());
			 * 
			 * 
			 * for (HSLFTextRun run1:text){ //XSLFTextRun run =
			 * text.addNewTextRun(); // PaintStyle.SolidPaint c = (SolidPaint)
			 * run1.getFontColor();
			 * 
			 * System.out.println("Text size: " + run1.getFontSize());
			 * System.out.println("Text family: " + run1.getFontFamily()); //
			 * System.out.println("Text color: " +
			 * c.getSolidColor().getColor());
			 * 
			 * }
			 * 
			 * } System.out.println(
			 * "-------------------------------------------------");
			 * 
			 * }
			 * 
			 * }
			 */
		}

	}

	public int getXMLNoOfWords(XSLFSlide slide) {

		List<XSLFShape> shapes = slide.getShapes();

		int word = 0;

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {
						if (!run1.getRawText().equals("")) {
							for (String x : run1.getRawText().split(" ")) {
								if (!x.equals("") || !x.equals(" "))
									word++;

							}

						}

					}
				}
			}
		}

		return word;
	}

	public String getXMLFontFamily(XSLFSlide slide) throws IOException {

		List<XSLFShape> shapes = slide.getShapes();

		String font = "";

		String family = getXMLFamily();

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {
						if (family.equals("")) {
							for (String f : sanSarifFont) {
								if (run1.getFontFamily().equalsIgnoreCase(f)) {

									font = "You are used recommandard San-Serif font";

									break;
								} else {

									font = "You are used non recommandard font called " + run1.getFontFamily()
											+ " for some parts. Please use san-sarif font to better readerbility";
								}
							}
						} else {
							if (run1.getFontFamily().equalsIgnoreCase(family)) {

								font = "You are used recommandard San-Serif font";

							} else {

								font = "You are used non recommandard font called " + run1.getFontFamily()
										+ " for some parts. Please use san-sarif font to better readerbility";
							}
						}
					}
				}
			}
		}
		if (font.equals("You are used recommandard San-Serif font")) {
			flag++;
		}

		return font;
	}

	public String getXMLFamily() throws IOException {

		String value = "";
		FileInputStream mfis = new FileInputStream(
				Paths.get("upload-master-dir") + "/" + slideStand.get(1).getMasterFirstName());
		XMLSlideShow mppt = new XMLSlideShow(mfis);
		List<XSLFSlide> masterSlide = mppt.getSlides();

		XSLFSlide slide = masterSlide.get(0);
		List<XSLFShape> shapes = slide.getShapes();
		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {
				List<XSLFTextParagraph> texts = ((XSLFTextShape) shape).getTextParagraphs();

				for (XSLFTextParagraph run : texts) {
					for (XSLFTextRun run1 : run) {

						if (!run1.getRawText().trim().equals(""))
							value = run1.getFontFamily();

					}
				}
			}

		}
		return value;
	}

	public boolean getXMLDifferentiation(XSLFSlide slide, String path, int number) throws IOException {

		boolean value = false;

		FileInputStream mfis = new FileInputStream(rootLocationMaster + "/" + path);
		XMLSlideShow mppt = new XMLSlideShow(mfis);
		XSLFSlide masterSlide = mppt.getSlides().get(number);
		// TexturePaint
		String slide_filltype = slide.getBackground().getFillStyle().toString();
		String master_filltype = masterSlide.getMasterSheet().getBackground().getFillStyle().toString();

		if (slide_filltype == null && master_filltype == null) {
			// if(slide.getBackground().getFill().getPictureData().toString().equals(masterSlide.getMasterSheet().getBackground().getFill().getPictureData().toString()))
			value = true;
		}

		System.out.println("DIf " + value);
		return value;
	}

	public String getXMLFontVisibility(XSLFSlide slide, double bblue, double bgreen, double bred) {

		List<XSLFShape> shapes = slide.getShapes();

		double backCalc = 0.0;
		String fontVisible = "";
		int fontRed, fontGreen, fontBlue = 0;

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				/*
				 * System.out.println(
				 * "shape fill color : "+textShape.getFillColor());
				 * System.out.println(
				 * "shape fill color text : "+textShape.getRawText());
				 */

				if (textShape.getFillColor() != null) {
					backCalc = ((textShape.getFillColor().getRed() * 299) + (textShape.getFillColor().getGreen() * 587)
							+ (textShape.getFillColor().getBlue() * 114)) / 1000;
				} else {
					backCalc = ((bred * 299) + (bgreen * 587) + (bblue * 114)) / 1000;
				}

				for (XSLFTextParagraph text : texts) {

					for (XSLFTextRun run1 : text) {
						if (!(run1.getRawText().equals("")) && !(run1.getRawText().equals(" "))
								&& !(run1.getRawText().trim().equals("*"))) {

							PaintStyle.SolidPaint c = (SolidPaint) run1.getFontColor();

							fontBlue = c.getSolidColor().getColor().getBlue();
							fontGreen = c.getSolidColor().getColor().getGreen();
							fontRed = c.getSolidColor().getColor().getRed();

							/*
							 * System.out.println( "fontBlue "+fontBlue);
							 * System.out.println( "fontGreen "+fontGreen);
							 * System.out.println( "fontRed "+fontRed);
							 * System.out.println( "bred "+bred);
							 * System.out.println( "bgreen "+bgreen);
							 * System.out.println( "bblue "+bblue);
							 */

							double fontCalc = ((fontRed * 299) + (fontGreen * 587) + (fontBlue * 114)) / 1000;

							if (((backCalc - fontCalc) < 125) && ((backCalc - fontCalc) > -125)) {
								fontVisible = run1.getRawText() + "," + fontVisible;
							}
						}
					}
				}
			}
		}

		return fontVisible;
	}

	public String getXMLFontSize(XSLFSlide slide) {

		List<XSLFShape> shapes = slide.getShapes();

		double fontSize = 0;
		String notice = "";

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {
						fontSize = run1.getFontSize();

						if (slideStand.get(1).getAudianceSize() > 200) {
							if (fontSize == 42 || fontSize == 36) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 42 for Title and point 36 for Content";
								break;
							}
						} else if (200 > slideStand.get(1).getAudianceSize()
								&& slideStand.get(1).getAudianceSize() > 50) {
							if (fontSize == 36 || fontSize == 28) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 36 for Title and point 28 for Content";
								break;
							}
						} else {
							if (fontSize == 32 || fontSize == 24) {
								notice = "you have used proper font Size";

							} else {
								notice = "your font sizes are not good.Please use point 32 for Title and point 24 for Content";
								break;
							}
						}

					}
				}
			}
		}

		if (notice.equals("you have used proper font Size")) {
			flag++;
		}
		return notice;
	}

	public String getXMLTextBoxPosition(XSLFSlide slide, String position) {

		List<XSLFShape> shapes = slide.getShapes();

		String notice = "";

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;

				if (position.equals("left")) {

					if (textShape.getAnchor().getX() < 80) {
						notice = "Good";

					} else {
						if (!textShape.getText().equals(""))
							notice = "Poor.Place your all textboxes in left side to get better attention";
						break;
					}

				} else if (position.equals("right")) {

					if (textShape.getAnchor().getX() > 400) {
						notice = "Good";
					} else {
						if (!textShape.getText().equals(""))
							notice = "Poor.Place your all textboxes in right side to get better attention";
						break;
					}
				} else {
					if (textShape.getAnchor().getX() < 400 && textShape.getAnchor().getX() > 200) {
						notice = "Good";
					} else {
						if (!textShape.getText().equals(""))
							notice = "Poor.Place your all textboxes in center to get better attention";
						break;

					}

				}
			}
		}

		if (notice.equals("Good")) {
			flag++;
		}
		return notice;
	}

	public int getXMLNoOfBullers(XSLFSlide slide) {

		List<XSLFShape> shapes = slide.getShapes();

		int bullert = 0;

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {

					if (text.isBullet()) {

						for (XSLFTextRun run1 : text) {
							if (!(run1.getRawText().trim().equals(""))) {
								if (!(run1.getRawText().trim().charAt(0) == ' ')) {
									// System.out.println(run1.getRawText());
									bullert++;
									break;
								}

							}
						}

					}
				}
			}
		}

		return bullert;
	}

	public boolean checkXMLMasterTempText(int value, String masterPath, String slidePath) throws IOException {

		boolean test = false;
		ArrayList<String> masterText = new ArrayList<String>();
		ArrayList<String> slideText = new ArrayList<String>();

		masterText = getXMLMasterTempText(value, masterPath);
		slideText = getXMLMasterTempText(value, slidePath);

		if (masterText.size() == slideText.size()) {
			for (int x = 0; x < masterText.size(); x++) {
				if (masterText.get(x).charAt(0) != '<') {
					if (masterText.get(x).equals(slideText.get(x))) {
						test = true;
					} else {
						test = false;
						break;
					}
				}
			}
		}
		return test;
	}

	public ArrayList<String> getXMLMasterTempText(int value, String path) throws IOException {
		ArrayList<String> masterTempText = new ArrayList<String>();

		FileInputStream mfis = new FileInputStream(path);
		XMLSlideShow mppt = new XMLSlideShow(mfis);
		List<XSLFSlideMaster> masterSlide = mppt.getSlideMasters();

		XSLFSlideMaster slide = masterSlide.get(0);// have to fix 0 should be
		List<XSLFShape> shapes = slide.getShapes(); // value

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {
				List<XSLFTextParagraph> texts = ((XSLFTextShape) shape).getTextParagraphs();

				for (XSLFTextParagraph run : texts) {
					for (XSLFTextRun run1 : run) {

						if (!run1.getRawText().trim().equals("") && !run1.getRawText().trim().equals("*")) {

							masterTempText.add(run1.getRawText());
							// System.out.println(run1.getRawText());

						}

					}
				}

			}
		}

		return masterTempText;
	}

	public boolean getXMLPicPosition(XSLFSlide slide, String path, int number) throws IOException {
		// changed
		FileInputStream mfis = new FileInputStream(rootLocationMaster + "/" + path);
		XMLSlideShow mppt = new XMLSlideShow(mfis);
		XSLFSlide masterSlide = mppt.getSlides().get(number);

		List<XSLFShape> shapes = slide.getMasterSheet().getShapes();
		List<XSLFShape> masterShapes = masterSlide.getMasterSheet().getShapes();

		boolean value = false;

		for (XSLFShape mshape : masterShapes) {
			if (mshape instanceof XSLFPictureShape) {

				XSLFPictureShape mpicShape = (XSLFPictureShape) mshape;

				double mHeght = mpicShape.getPictureData().getImageDimension().getHeight();
				double mWidth = mpicShape.getPictureData().getImageDimension().getWidth();
				double mX = mpicShape.getAnchor().getX();
				double mY = mpicShape.getAnchor().getY();

				for (XSLFShape shape : shapes) {
					if (shape instanceof XSLFPictureShape) {

						XSLFPictureShape picShape = (XSLFPictureShape) shape;

						double heght = picShape.getPictureData().getImageDimension().getHeight();
						double width = picShape.getPictureData().getImageDimension().getWidth();
						double x = picShape.getAnchor().getX();
						double y = picShape.getAnchor().getY();

						if ((mHeght == heght) && (mWidth == width) && (mX == x) && (mY == y)) {
							value = true;
						}

					}
				}
				if (!value) {
					return false;
				}

			}
		}

		return value;
	}

	public static void convertToXMLImage(String path, int slideNo) {

		BufferedImage img = null;
		// File file = new File(Paths.get("upload-dir")+"/"+path);
		File file = new File(path);
		XMLSlideShow ppt;
		int x = 0;
		try {
			ppt = new XMLSlideShow(new FileInputStream(file));
			// getting the dimensions and size of the slide
			Dimension pgsize = ppt.getPageSize();

			// System.out.println("slide no:"+slideNo);
			XSLFSlide i = ppt.getSlides().get(slideNo);
			img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = img.createGraphics();

			// clear the drawing area
			graphics.setPaint(Color.white);
			graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

			// render
			i.draw(graphics);

			// creating an image file as output
			FileOutputStream out = new FileOutputStream(rootLocationSlideImage + "/" + "ppt_image" + slideNo + ".png");
			javax.imageio.ImageIO.write(img, "png", out);
			// ppt.write(out);
			// x++;
			System.out.println("Image successfully created");
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readXMLCustomPPT() throws Exception {

		float total = 0;
		int x = 1;
		convertToXMLImage(Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(), 0);
		convertToXMLImage(Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(), 1);
		double[] rgb1 = imageProcessing(0);
		double[] rgb2 = imageProcessing(1);

		FileInputStream fis = new FileInputStream(rootLocation + "/" + slideCustom.get(1).getMaterialName());
		XMLSlideShow ppt = new XMLSlideShow(fis);

		for (XSLFSlide slide : ppt.getSlides()) {

			SlideReport reportItem = new SlideReport();

			reportItem.setFooter("Footer feature is not available for pptx files");
			reportItem.setFootertext("Footer feature is not available for pptx files");

			reportItem.setHeader("Header feature is not available for pptx files");
			reportItem.setHeadertext("Header feature is not available for pptx files");

			reportItem.setPageNumber("Slide Numbering  feature is not available for pptx files");

			reportItem.setFontFamily(getXMLCustomFontFamily(slide));

			if (!slideCustom.get(1).getBcolor().equals("null") && !slideCustom.get(1).getFcolor().equals("null")) {

				Color bc = hex2Rgb(slideCustom.get(1).getBcolor());
				Color fc = hex2Rgb(slideCustom.get(1).getFcolor());
				reportItem.setContrast(getXMLFontCustomVisibility(slide, bc.getRed(), bc.getBlue(), bc.getGreen(),
						fc.getRed(), fc.getBlue(), fc.getGreen()));

			} else {

				if (x == 1) {

					if (getXMLDifferentiation(slide, slideCustom.get(1).getMasterFirstName(), 0)) {

						if (getXMLFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getXMLFontVisibility(slide, rgb1[0], rgb1[1], rgb1[2]));
						}

					} else {
						convertToXMLImage(Paths.get("upload-dir") + "/" + slideCustom.get(1).getMaterialName(),
								slide.getSlideNumber() - 1);
						double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

						if (getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
						}
					}

				} else {

					if (getXMLDifferentiation(slide, slideCustom.get(1).getMasterFirstName(), 1)) {

						if (getXMLFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getXMLFontVisibility(slide, rgb2[0], rgb2[1], rgb2[2]));
						}

					} else {
						convertToImage(Paths.get("upload-dir") + "/" + slideCustom.get(1).getMaterialName(),
								slide.getSlideNumber() - 1);
						double[] normalRgb = imageProcessing(slide.getSlideNumber() - 1);

						if (getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]).equals("")) {
							reportItem.setContrast("Visibility is Good");
							flag++;
						} else {
							reportItem.setContrast("Visibility is Poor in these words : "
									+ getXMLFontVisibility(slide, normalRgb[0], normalRgb[1], normalRgb[2]));
						}
					}
				}
			}

			reportItem.setSlideNo(x);

			if (slideCustom.get(1).getNoofword() > 36) {
				reportItem.setFontSize("Can't calculate font size, Because you have exceeded recommended no of words");
			} else if (slideCustom.get(1).getBullert() > 6) {
				reportItem
						.setFontSize("Can't calculate font size, Because you have exceeded recommended no of bullets");
			} else {
				reportItem.setFontSize(getXMLCustomFontSize(slide));
			}

			reportItem.setPosition(
					"TextBox position is " + getXMLTextBoxPosition(slide, slideCustom.get(1).getPosition()));

			if (ppt.getSlides().size() <= slideCustom.get(1).getNoofslide()) {
				reportItem.setMaxSlideNo("Number of slides are " + ppt.getSlides().size());
				flag++;
			} else {
				reportItem.setMaxSlideNo("Number of slides are " + ppt.getSlides().size()
						+ ". Try to maintain number of slide less than " + slideCustom.get(1).getNoofslide());
			}

			if (getXMLNoOfWords(slide) <= slideCustom.get(1).getNoofword()) {
				reportItem.setWords("Number of words per slide is good");
				flag++;
			} else {
				reportItem.setWords("Number of words per slide is " + getXMLNoOfWords(slide)
						+ ". Try to maintain number of words per slide less than " + slideCustom.get(1).getNoofword());
			}
			if (getXMLNoOfBullers(slide) <= slideCustom.get(1).getBullert()) {
				reportItem.setBullters("Number of bullerts per slide is good");
				flag++;
			} else {
				reportItem.setBullters("Number of bullerts per slide is " + getXMLNoOfBullers(slide)
						+ ". Try to maintain number of bullerts per slide less than "
						+ slideCustom.get(1).getBullert());
			}

			if (x == 1) {
				if (checkXMLMasterTempText(0,
						Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(),
						rootLocation + "/" + slideCustom.get(1).getMaterialName())
						&& getXMLPicPosition(slide, slideCustom.get(1).getMasterFirstName(), 0)) {
					reportItem.setPicPositions("Slide match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not match with logo and images in Master slide");
				}
			} else {
				if (checkXMLMasterTempText(1,
						Paths.get("upload-master-dir") + "/" + slideCustom.get(1).getMasterFirstName(),
						rootLocation + "/" + slideCustom.get(1).getMaterialName())
						&& getXMLPicPosition(slide, slideCustom.get(1).getMasterFirstName(), 1)) {
					reportItem.setPicPositions("Slide match with logo and images in Master slide");
					flag++;
				} else {
					reportItem.setPicPositions("Slide does not match with logo and images in Master slide");
				}
			}

			if (flag == 10) {// 13
				reportItem.setFlag("true");
			} else {
				reportItem.setFlag("false");
			}

			System.out.println("flag " + flag);

			reportItem.setCheckingPoints(flag);

			float divide = (float) ((float) flag / (float) 10) * 100;
			System.out.println("divide " + divide);

			total = total + divide;

			System.out.println(numberFormat.format(total / ppt.getSlides().size()));
			if (x == ppt.getSlides().size()) {
				reportItem.setSummary(numberFormat.format(total / ppt.getSlides().size()));
			} else {
				reportItem.setSummary(numberFormat.format(divide));
			}

			this.slideReport.put(x, reportItem);
			flag = 0;
			x++;

		}
	}

	public String getXMLCustomFontSize(XSLFSlide slide) {

		List<XSLFShape> shapes = slide.getShapes();

		double fontSize = 0;
		String notice = "";

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {
						fontSize = run1.getFontSize();

						if (slideCustom.get(1).getCaudiance() > 200) {
							if (fontSize == 42 || fontSize == 36) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 42 for Title and point 36 for Content";
								break;
							}
						} else if (200 > slideCustom.get(1).getCaudiance() && slideCustom.get(1).getCaudiance() > 50) {
							if (fontSize == 36 || fontSize == 28) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 36 for Title and point 28 for Content";
								break;
							}
						} else {
							if (fontSize == 32 || fontSize == 24) {
								notice = "you have used proper font Size";
							} else {
								notice = "your font sizes are not good.Please use point 32 for Title and point 24 for Content";
								break;
							}
						}

					}
				}
			}
		}
		if (notice.equals("you have used proper font Size")) {
			flag++;
		}
		return notice;
	}

	public String getXMLCustomFontFamily(XSLFSlide slide) {

		List<XSLFShape> shapes = slide.getShapes();
		String f = slideCustom.get(1).getFontfamily();

		String font = "";

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {

						if (run1.getFontFamily().equalsIgnoreCase(f)) {

							font = "You are used recommandard San-Serif font";
							break;
						} else {

							font = "You are used non recommandard font called " + run1.getFontFamily()
									+ " for some parts. Please use san-sarif font called " + f
									+ " to better readerbility";
						}

					}
				}
			}
		}
		if (font.equals("You are used recommandard San-Serif font")) {
			flag++;
		}

		return font;
	}

	private String getXMLFontCustomVisibility(XSLFSlide slide, double bred, double bblue, double bgreen, double fred,
			double fblue, double fgreen) {

		List<XSLFShape> shapes = slide.getShapes();

		String infor, fcolorinfo = "";
		String font = "";
		String bcolorinfo = "";
		int fontRed, fontGreen, fontBlue, backRed, backGreen, backBlue = 0;

		backBlue = slide.getBackground().getFillColor().getBlue();
		backRed = slide.getBackground().getFillColor().getRed();
		backGreen = slide.getBackground().getFillColor().getGreen();

		if ((backBlue == bblue) && (backGreen == bgreen) && (backRed == bred)) {
			bcolorinfo = "Background match with color";
		} else {
			bcolorinfo = "Background doesn't match with color";
			;
		}

		for (XSLFShape shape : shapes) {
			if (shape instanceof XSLFTextShape) {

				XSLFTextShape textShape = (XSLFTextShape) shape;
				List<XSLFTextParagraph> texts = textShape.getTextParagraphs();

				for (XSLFTextParagraph text : texts) {
					for (XSLFTextRun run1 : text) {
						if (!(run1.getRawText().equals("")) && !(run1.getRawText().equals(" "))
								&& !(run1.getRawText().trim().equals("*"))) {

							PaintStyle.SolidPaint c = (SolidPaint) run1.getFontColor();
							fontBlue = c.getSolidColor().getColor().getBlue();
							fontGreen = c.getSolidColor().getColor().getGreen();
							fontRed = c.getSolidColor().getColor().getRed();

							if ((fontBlue == fblue) && (fontGreen == fgreen) && (fontRed == fred)) {
								fcolorinfo = "Fonts match with color";
							} else {
								font = font + "," + run1.getRawText();
							}
						}
					}
				}
			}
		}

		if (fcolorinfo.equals("Fonts match with color")) {
			infor = bcolorinfo + " and " + fcolorinfo;
		} else {
			infor = bcolorinfo + " and thses fonts does not match with color (" + font + ")";
		}

		return infor;
	}

	// docx part
	public void readXMLStandardDoc() {

		try {
			String mPath = rootLocationDocMaster + "/" + docStand.get(1).getMasterFirstName();
			String path = rootLocation + "/" + docStand.get(1).getMaterialName();
			DocReport reportItem = new DocReport();
			getXMLMasterDocParameters(mPath);
			getXMLDocParameters(path);

			if (docHeaderText()) {
				System.out.println("header is correct");
				reportItem.setHeader("ok");
			} else {
				reportItem.setHeader("no");
			}

			if (docFooterText()) {
				System.out.println("footer is correct");
				reportItem.setFooter("ok");
			} else {
				reportItem.setFooter("no");
			}

			String fontFamily = getXMLMasterDocFontFamily(mPath);

			if (checkXMLDocFontFamily(path, fontFamily)) {
				System.out.println("family is correct");
				reportItem.setFamily("ok");
			} else {
				reportItem.setFamily("no");
			}

			if (checkXMLTemplate()) {
				System.out.println("template is correct");
				reportItem.setTemplate("ok");
			} else {
				reportItem.setTemplate("no");
			}

			this.docReport.put(1, reportItem);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	ArrayList<Integer> mpicWDoc = new ArrayList<Integer>();
	ArrayList<Integer> mpicHDoc = new ArrayList<Integer>();
	ArrayList<Integer> picWDoc = new ArrayList<Integer>();
	ArrayList<Integer> picHDoc = new ArrayList<Integer>();
	
	public void getXMLMasterDocParameters(String path) throws IOException {

		FileInputStream mdoc = new FileInputStream(path);
		HWPFDocument doc = new HWPFDocument(mdoc);
		WordExtractor extractor = new WordExtractor(doc);

		String header = extractor.getHeaderText();
		String[] headerWords = header.split(" ");
		for (String value : headerWords) {
			if(!value.trim().equals(""))
			mHeaderText.add(value.trim());
		}

		String footer = extractor.getFooterText();
		String[] footerWords = footer.split(" ");
		for (String value : footerWords) {
			if(!value.trim().equals(""))
			mFooterText.add(value.trim());
		}

		for (Picture p : doc.getPicturesTable().getAllPictures()){
		mpicHDoc.add(p.getHeight());
		mpicWDoc.add(p.getHeight());
		}
		
		
	}
	
	public void getXMLDocParameters(String path) throws IOException {

		FileInputStream mdoc = new FileInputStream(path);
		HWPFDocument doc = new HWPFDocument(mdoc);
		WordExtractor extractor = new WordExtractor(doc);

		String header = extractor.getHeaderText();
		String[] headerWords = header.split(" ");
		for (String value : headerWords) {

			if(!value.trim().equals(""))
			headerText.add(value.trim());
		}

		String footer = extractor.getFooterText();
		String[] footerWords = footer.split(" ");
		for (String value : footerWords) {
			if(!value.trim().equals(""))
			footerText.add(value.trim());
		}

		for (Picture p : doc.getPicturesTable().getAllPictures()){
		picHDoc.add(p.getHeight());
		picWDoc.add(p.getHeight());
		}
		
		
		
	}

	public String getXMLMasterDocFontFamily(String path) throws IOException {

		String value = "";
		FileInputStream docP = new FileInputStream(path);
		HWPFDocument doc = new HWPFDocument(docP);

		 org.apache.poi.hwpf.usermodel.Range range=doc.getRange();
		 Paragraph paragraph = range.getParagraph(0);
		 CharacterRun run = paragraph.getCharacterRun(0);
		 value=run.getFontName();
		 
		return value;
	}
	
	
	public boolean checkXMLDocFontFamily(String path, String family) throws IOException {

		boolean value = true;
		FileInputStream docP = new FileInputStream(path);
		HWPFDocument doc = new HWPFDocument(docP);

		if (family.trim().equals("")) {

			for (String f : sanSarifFont) {

				
				 org.apache.poi.hwpf.usermodel.Range range=doc.getRange();
				 Paragraph paragraph = range.getParagraph(0);
				 
				 for (int j = 0; j < range.getParagraph(0).numCharacterRuns(); j++){
					 CharacterRun run = paragraph.getCharacterRun(0);
					 
					 if (f.equals(run.getFontName())) {
							value = false;
							break;
						}
				 }
				
				
			}

		} else {

			 org.apache.poi.hwpf.usermodel.Range range=doc.getRange();
			 Paragraph paragraph = range.getParagraph(0);
			 
			 for (int j = 0; j < range.getParagraph(0).numCharacterRuns(); j++){
				 CharacterRun run = paragraph.getCharacterRun(0);
				 
				 if (!family.equals(run.getFontName())) {
						value = false;
						break;
					}
			 }
			
		}
		return value;
	}

	
	public boolean checkXMLTemplate() {

		boolean value = true;
		if ((mpicWDoc.size() == picWDoc.size()) && (mpicHDoc.size() == picHDoc.size())) {

			for (int x = 0; x < picWDoc.size(); x++) {

				if (!(picWDoc.get(x).equals(mpicWDoc.get(x))) || !(picHDoc.get(x).equals(mpicHDoc.get(x)))) {

					value = false;
					break;
				}
			}
		} else {
			value = false;
		}

		return value;
	}
	
	public void readXMLCustomDoc() {

		try {
			String mPath = rootLocationDocMaster + "/" + docCustom.get(1).getMasterFirstName();
			String path = rootLocation + "/" + docCustom.get(1).getMaterialName();
			DocReport reportItem = new DocReport();
			getXMLMasterDocParameters(mPath);
			getXMLDocParameters(path);

			if (docCustom.get(1).getHeader().equals("on")) {

				if (docHeaderText()) {
					System.out.println("header is correct");
					reportItem.setHeader("ok");
				} else {
					reportItem.setHeader("no");
				}

			} else {
				reportItem.setHeader("not available");
			}

			if (docCustom.get(1).getFooter().equals("on")) {

				if (docFooterText()) {
					System.out.println("footer is correct");
					reportItem.setFooter("ok");
				} else {
					reportItem.setFooter("no");
				}

			} else {
				reportItem.setFooter("not available");
			}

			if (!docCustom.get(1).getFontfamily().equals("null")) {

				String fontFamily = docCustom.get(1).getFontfamily();

				if (checkXMLDocFontFamily(path, fontFamily)) {
					System.out.println("family is correct");
					reportItem.setFamily("ok");
				} else {
					reportItem.setFamily("no");
				}

			} else {
				String fontFamily = "";

				if (checkXMLDocFontFamily(path, fontFamily)) {
					System.out.println("family is correct");
					reportItem.setFamily("ok");
				} else {
					reportItem.setFamily("no");
				}
			}

			if (checkXMLTemplate()) {
				System.out.println("template is correct");
				reportItem.setTemplate("ok");
			} else {
				reportItem.setTemplate("no");
			}

			this.docReport.put(1, reportItem);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	
}
