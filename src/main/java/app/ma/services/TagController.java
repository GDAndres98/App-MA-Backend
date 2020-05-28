package app.ma.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Article;
import app.ma.entities.Tag;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.SectionRepository;
import app.ma.repositories.TagRepository;

@RestController
public class TagController {
	
	
	@Autowired private TagRepository tagRepository;
	@Autowired private ArticleRepository articleRepository;
	@Autowired private SectionRepository sectionRepository;
	@Autowired private CourseRepository  courseRepository;

	
	@RequestMapping("/getAllTags")
	public Iterable<Tag> getAllTags () {
		
		Iterable<Tag> findAll = tagRepository.findAll();
		return findAll;
	}
	
	@RequestMapping(path="/getTagById", method=RequestMethod.GET)
	public Tag getTagById 
	(
			@RequestHeader Long id) {
		
		Optional<Tag> tag = tagRepository.findById(id);
		if(!tag.isPresent())
			return null;
		return tag.get();
	}
	
	@RequestMapping(path="/getTagsByLevel", method=RequestMethod.GET)
	public Iterable<Tag> getTagsByLevel 
	(
			@RequestHeader Long level) {
		Iterable<Tag> findAll = tagRepository.findByLevel(level);
		return findAll;
	}
	
	@RequestMapping(path="/createTag", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createTag
	(
			@RequestParam String 	name, 
			@RequestParam Long 	level) {
		if(name.trim().isEmpty()) return new ResponseEntity<>("Nombre en blanco.", HttpStatus.BAD_REQUEST);
		
		Tag tag = new Tag();
		tag.setName(name);
		tag.setLevel(level);
		
		tagRepository.save(tag);
		
		return new ResponseEntity<>("Tag creado satisfactoriamente.", HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/deleteTagById", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createTag
	(
			@RequestParam Long 	id) {
		Optional<Tag> opTag = tagRepository.findById(id);
		if (!opTag.isPresent())
			return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		
		tagRepository.deleteById(id);
		
		return new ResponseEntity<>("Tag eliminado satisfactoriamente.", HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/getArticlesByTagId", method=RequestMethod.GET)
	public Iterable<Article> getArticlesByTagId 
	(
			@RequestHeader Long id) {

		Optional<Tag> opTag = tagRepository.findById(id);
		if(!opTag.isPresent()) return null;
		
		return opTag.get().getArticles();
		
	}
	
	@RequestMapping(path="/addTagtoArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addTagtoArticle
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		articleId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if(!opTag.isPresent()) return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if(!opArticle.isPresent()) return new ResponseEntity<>("Artículo no existe.", HttpStatus.BAD_REQUEST);
		Article article = opArticle.get();
		Tag tag = opTag.get();
		
		tag.addArticle(article);
		article.addTag(tag);
		
		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Agregado correctamente.", HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(path="/removeTagofArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeTagofArticle
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		articleId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if(!opTag.isPresent()) return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if(!opArticle.isPresent()) return new ResponseEntity<>("Artículo no existe.", HttpStatus.BAD_REQUEST);
		Article article = opArticle.get();
		Tag tag = opTag.get();
		
		tag.removeArticle(article);
		article.removeTag(tag);
		
		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Desasociado correctamente.", HttpStatus.ACCEPTED);
	}
	
}
