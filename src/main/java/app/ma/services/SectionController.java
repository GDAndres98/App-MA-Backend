package app.ma.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.Section;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.SectionRepository;

@RestController
public class SectionController {
	
	@Autowired private SectionRepository sectionRepository;
	@Autowired private CourseRepository  courseRepository;
	@Autowired private ArticleRepository articleRepository;

	@CrossOrigin
	@RequestMapping("/getAllSections")
	public Iterable<Section> getAllSections () {
		
		Iterable<Section> findAll = sectionRepository.findAll();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getSectionById", method=RequestMethod.GET)
	public Section getSectionById 
	(
			@RequestHeader Long id) {
		
		Optional<Section> section = sectionRepository.findById(id);
		if(!section.isPresent())
			return null;
		return section.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getSectionsByCourseId", method=RequestMethod.GET)
	public Iterable<Section> getSectionsByCourseId 
	(
			@RequestHeader Long id) {
		Iterable<Section> sections = sectionRepository.findByPostedAt_Id(id);
		return sections;
	}
	
	@CrossOrigin
	@RequestMapping(path="/createSection", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createSection 
	(
			@RequestParam String 	title	, 
			@RequestParam String 	description, 
			@RequestParam Long 	courseId) {
		if(title.trim().isEmpty()) return new ResponseEntity<>("Titulo en blanco.", HttpStatus.BAD_REQUEST);
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if(!opCourse.isPresent()) return new ResponseEntity<>("Curso no valido.", HttpStatus.BAD_REQUEST);
		Course course = opCourse.get();
		
		Section section = new Section();
		section.setTitle(title);
		section.setDescription(description);
		section.setPostedAt(course);
		
		sectionRepository.save(section);
		
		return new ResponseEntity<>("Seccion creada satisfactoriamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/addArticleToSection", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addArticleToSection
	(
			@RequestParam Long 		articleId,  
			@RequestParam Long 		sectionId) {
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if(!opArticle.isPresent()) return new ResponseEntity<>("Articulo no existe.", HttpStatus.BAD_REQUEST);
		Optional<Section> opSection = sectionRepository.findById(sectionId);
		if(!opSection.isPresent()) return new ResponseEntity<>("Seccion no existe.", HttpStatus.BAD_REQUEST);
		Section section = opSection.get();
		Article article = opArticle.get();

		section.addArticle(article);
		article.addSection(section);
		
		sectionRepository.save(section);

		return new ResponseEntity<>("Articulo Agregado correctamente.", HttpStatus.ACCEPTED);
	}
	
	
	
	
}
