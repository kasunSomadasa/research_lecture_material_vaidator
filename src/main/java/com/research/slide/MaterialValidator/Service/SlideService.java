package com.research.slide.MaterialValidator.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import com.research.slide.MaterialValidator.Dao.SlideDao;
import com.research.slide.MaterialValidator.Entity.CustomDoc;
import com.research.slide.MaterialValidator.Entity.DocReport;
import com.research.slide.MaterialValidator.Entity.Slide;
import com.research.slide.MaterialValidator.Entity.SlideCustom;
import com.research.slide.MaterialValidator.Entity.SlideReport;
import com.research.slide.MaterialValidator.Entity.SlideStandard;
import com.research.slide.MaterialValidator.Entity.StandardDoc;

@Service
@CrossOrigin(origins = "*")
public class SlideService {

	@Autowired
	private SlideDao slideDao;
	
	public Collection<Slide> getAllSlides(){
		return this.slideDao.getAllSlides();
	}
	
	public Slide getSlideByID(int id){
		return this.slideDao.getSlideByID(id);
	}

	public void insertSlide(Slide slide) {
		this.slideDao.insertSlide(slide);
	}
	
	public void store(MultipartFile file)  {
		this.slideDao.store(file);
	}
	
	public void storeMasterSlide(MultipartFile file)  {
		this.slideDao.storeMasterSlide(file);
	}
	
	private final Path rootLocation = Paths.get("upload-dir");
	private final Path rootLocationMaster = Paths.get("upload-master-dir");
	private final Path rootLocationDocMaster = Paths.get("upload-doc-master-dir");
	private final Path rootLocationSlideImage = Paths.get("slide-image-dir");
	
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
 
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		FileSystemUtils.deleteRecursively(rootLocationMaster.toFile());
		FileSystemUtils.deleteRecursively(rootLocationDocMaster.toFile());
		FileSystemUtils.deleteRecursively(rootLocationSlideImage.toFile());
	}
	public void deleteUplaods() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		FileSystemUtils.deleteRecursively(rootLocationSlideImage.toFile());
	}
 
	public void createDirectory() {
		try {
			Files.createDirectory(rootLocation);
			Files.createDirectory(rootLocationSlideImage);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
	
	public void init() {
		try {
			Files.createDirectory(rootLocation);
			Files.createDirectory(rootLocationMaster);
			Files.createDirectory(rootLocationDocMaster);
			Files.createDirectory(rootLocationSlideImage);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}

	public void insertStandardSlide(SlideStandard slide) {
		this.slideDao.insertStandardSlide(slide);
		
	}

	public Collection<SlideReport> validateStandardSlide() {
		return slideDao.validateStandardSlide();
	}

	public void insertCustomSlide(SlideCustom slide) {
		this.slideDao.insertCustomSlide(slide);
		
	}

	public Collection<SlideReport> validateCustomSlide() {
		return slideDao.validateCustomSlide();
	}

	public void validateDocFile() {
		this.slideDao.readDoc();
		
	}

	public void insertStandardDoc(StandardDoc doc) {
		this.slideDao.insertStandardDoc(doc);		
	}
	
	public void insertCustomDoc(CustomDoc doc) {
		this.slideDao.insertCustomDoc(doc);		
	}

	public void storeMasterDoc(MultipartFile file) {
		this.slideDao.storeMasterDoc(file);	
	}

	public Collection<DocReport> validateStandardDoc() {
		return slideDao.validateStandardDoc();
	}

	public Collection<DocReport> validateCustomDoc() {
		return slideDao.validateCustomDoc();
	}
}
