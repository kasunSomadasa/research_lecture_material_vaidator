package com.research.slide.MaterialValidator.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.research.slide.MaterialValidator.Entity.AdminDocTemplate;
import com.research.slide.MaterialValidator.Entity.CustomDoc;
import com.research.slide.MaterialValidator.Entity.DocReport;
import com.research.slide.MaterialValidator.Entity.DocTemplateRepository;
import com.research.slide.MaterialValidator.Entity.Slide;
import com.research.slide.MaterialValidator.Entity.SlideAdminTemplate;
import com.research.slide.MaterialValidator.Entity.SlideCustom;
import com.research.slide.MaterialValidator.Entity.SlideReport;
import com.research.slide.MaterialValidator.Entity.SlideStandard;
import com.research.slide.MaterialValidator.Entity.SlideTemplateRepository;
import com.research.slide.MaterialValidator.Entity.StandardDoc;
import com.research.slide.MaterialValidator.Service.SlideService;

@RestController
@RequestMapping("/slide")
@CrossOrigin(origins = "*")
public class SlideController {

	SlideTemplateRepository repo;
	DocTemplateRepository docRepo;

	@Autowired
	private SlideService slideService;

	public SlideController(SlideTemplateRepository repo, DocTemplateRepository docRepo) {
		this.repo = repo;
		this.docRepo = docRepo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Slide> getAllSlides() {

		nu.pattern.OpenCV.loadShared();
		Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
		System.out.println("mat = " + mat.dump());

		return slideService.getAllSlides();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Slide getSlideByID(@PathVariable("id") int id) {
		return slideService.getSlideByID(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertSlide(@RequestBody Slide slide) {
		slideService.insertSlide(slide);
	}

	List<String> files = new ArrayList<String>();

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<String> store(@RequestParam("file") MultipartFile file) {
		slideService.deleteUplaods();
		slideService.createDirectory();
		String message = "";
		try {

			if (file.getContentType().equalsIgnoreCase("application/vnd.ms-powerpoint")
					|| file.getContentType().equalsIgnoreCase(
							"application/vnd.openxmlformats-officedocument.presentationml.presentation")
					|| file.getContentType().equalsIgnoreCase("application/msword")
					|| file.getContentType().equalsIgnoreCase(
							"application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				slideService.store(file);
				files.add(file.getOriginalFilename());

				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			} else {
				message = "error";
			}
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@RequestMapping(value = "/postMasterSlide", method = RequestMethod.POST)
	public ResponseEntity<String> storeMasterSlide(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			

				if (file.getContentType().equalsIgnoreCase("application/vnd.ms-powerpoint")
						|| file.getContentType().equalsIgnoreCase(
								"application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
					slideService.storeMasterSlide(file);
					files.add(file.getOriginalFilename());
					System.out.println(file.getOriginalFilename());
					message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				} else {
					message = "error";
				}

				return ResponseEntity.status(HttpStatus.OK).body(message);
				} catch (Exception e) {
					message = "FAIL to upload " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
				}
	}

	@RequestMapping(value = "/slideStandard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertStandardSlide(@RequestBody SlideStandard slide) {
		slideService.insertStandardSlide(slide);
	}

	@RequestMapping(value = "/slideCustom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertCustomSlide(@RequestBody SlideCustom slide) {
		slideService.insertCustomSlide(slide);
	}

	@RequestMapping(value = "/slideStandard", method = RequestMethod.GET)
	public Collection<SlideReport> validateStandardSlide() {

		return slideService.validateStandardSlide();
	}

	@RequestMapping(value = "/slideCustom", method = RequestMethod.GET)
	public Collection<SlideReport> validateCustomSlide() {

		return slideService.validateCustomSlide();
	}

	@PutMapping("/mongo")
	public void insertTemplate(@RequestBody SlideAdminTemplate temp) {
		this.repo.insert(temp);
	}

	@PostMapping("/mongo")
	public void updateTemplate(@RequestBody SlideAdminTemplate temp) {
		this.repo.save(temp);
	}

	@GetMapping("/mongo/{name}")
	public Collection<SlideAdminTemplate> getTempalte(@PathVariable("name") String name) {
		return this.repo.findByUniversityLike(name);
	}

	@RequestMapping(value = "/doc", method = RequestMethod.GET)
	public void validateDocFile() {
		slideService.validateDocFile();
	}

	@RequestMapping(value = "/docStandard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertStandardDoc(@RequestBody StandardDoc doc) {
		slideService.insertStandardDoc(doc);
	}

	@RequestMapping(value = "/docCustom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertCustomDoc(@RequestBody CustomDoc doc) {
		slideService.insertCustomDoc(doc);
	}

	@RequestMapping(value = "/postMasterDoc", method = RequestMethod.POST)
	public ResponseEntity<String> storeMasterDoc(@RequestParam("file") MultipartFile[] uploadedFiles) {
		String message = "";
		try {

			for (MultipartFile file : uploadedFiles) {

				if (file.getContentType().equalsIgnoreCase("application/msword") || file.getContentType()
						.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
					slideService.storeMasterDoc(file);
					files.add(file.getOriginalFilename());
					System.out.println(file.getOriginalFilename());

					message = "You successfully uploaded !";
				} else {
					message = "error";
					break;

				}
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			// message = "FAIL to upload " + file.getOriginalFilename() + "!";
			message = "FAIL to upload !";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/doc/mongo")
	public void updateDocTemplate(@RequestBody AdminDocTemplate temp) {
		this.docRepo.save(temp);
	}

	@GetMapping("/doc/mongo/{name}")
	public Collection<AdminDocTemplate> getDocTempalte(@PathVariable("name") String name) {
		return this.docRepo.findByUniversityLike(name);
	}

	@GetMapping("/doc/mongo/check/{name}")
	public Collection<AdminDocTemplate> getDocCheck(@PathVariable("name") String name) {
		return this.docRepo.findByNameLike(name);
	}
	
	@GetMapping("/mongo/check/{name}")
	public Collection<SlideAdminTemplate> getSlideCheck(@PathVariable("name") String name) {
		return this.repo.findByNameLike(name);
	}
	
	@GetMapping("/doc/mongo/checkMaster/{name}")
	public Collection<AdminDocTemplate> getDocMasterCheck(@PathVariable("name") String name) {
		return this.docRepo.findByActualNameLike(name);
	}
	
	@GetMapping("/mongo/checkMaster/{name}")
	public Collection<SlideAdminTemplate> getSlideMasterCheck(@PathVariable("name") String name) {
		return this.repo.findByActualNameLike(name);
	}
	
	
	@RequestMapping(value = "/docStandard", method = RequestMethod.GET)
	public Collection<DocReport> validateStandardDoc() {

		return slideService.validateStandardDoc();
	}

	@RequestMapping(value = "/docCustom", method = RequestMethod.GET)
	public Collection<DocReport> validateCustomDoc() {

		return slideService.validateCustomDoc();
	}
}
