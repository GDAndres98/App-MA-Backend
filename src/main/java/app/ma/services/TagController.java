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
import app.ma.entities.Problem;
import app.ma.entities.Tag;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.TagRepository;

@RestController
public class TagController {
	
	
	@Autowired private TagRepository tagRepository;
	@Autowired private ArticleRepository articleRepository;
	@Autowired private ProblemRepository problemRepository;

	@CrossOrigin
	@RequestMapping("/getAllTags")
	public Iterable<Tag> getAllTags () {
		
		Iterable<Tag> findAll = tagRepository.findAll();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getTagById", method=RequestMethod.GET)
	public Tag getTagById 
	(
			@RequestHeader Long id) {
		
		Optional<Tag> tag = tagRepository.findById(id);
		if(!tag.isPresent())
			return null;
		return tag.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getTagsByLevel", method=RequestMethod.GET)
	public Iterable<Tag> getTagsByLevel 
	(
			@RequestHeader Long level) {
		Iterable<Tag> findAll = tagRepository.findByLevel(level);
		return findAll;
	}
	
	@CrossOrigin
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
	
	@CrossOrigin
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
	
	@CrossOrigin
	@RequestMapping(path="/getArticlesByTagId", method=RequestMethod.GET)
	public Iterable<Article> getArticlesByTagId 
	(
			@RequestHeader Long id) {

		Optional<Tag> opTag = tagRepository.findById(id);
		if(!opTag.isPresent()) return null;
		
		return opTag.get().getArticles();
		
	}
	
	@CrossOrigin
	@RequestMapping(path="/addTagtoArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addTagtoArticle
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		articleId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if (!opTag.isPresent())
			return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if (!opArticle.isPresent())
			return new ResponseEntity<>("Artículo no existe.", HttpStatus.BAD_REQUEST);
		Article article = opArticle.get();
		Tag tag = opTag.get();

		tag.addArticle(article);
		article.addTag(tag);

		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Agregado correctamente.", HttpStatus.ACCEPTED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/removeTagofArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeTagofArticle
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		articleId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if (!opTag.isPresent())
			return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if (!opArticle.isPresent())
			return new ResponseEntity<>("Artículo no existe.", HttpStatus.BAD_REQUEST);
		Article article = opArticle.get();
		Tag tag = opTag.get();

		tag.removeArticle(article);
		article.removeTag(tag);

		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Desasociado correctamente.", HttpStatus.ACCEPTED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getProblemsByTagId", method=RequestMethod.GET)
	public Iterable<Problem> getProblemsByTagId 
	(
			@RequestHeader Long id) {

		Optional<Tag> opTag = tagRepository.findById(id);
		if(!opTag.isPresent()) return null;
		
		return opTag.get().getProblems();
		
	}
	
	@CrossOrigin
	@RequestMapping(path="/addTagtoProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addTagtoProblem
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		problemId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if (!opTag.isPresent())
			return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if (!opProblem.isPresent())
			return new ResponseEntity<>("Problema no existe.", HttpStatus.BAD_REQUEST);
		Problem problem = opProblem.get();
		Tag tag = opTag.get();

		tag.addProblem(problem);
		problem.addTag(tag);

		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Agregado correctamente.", HttpStatus.ACCEPTED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/removeTagofProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeTagofProblem
	(
			@RequestParam Long 		tagId,  
			@RequestParam Long 		problemId) {
		Optional<Tag> opTag = tagRepository.findById(tagId);
		if (!opTag.isPresent())
			return new ResponseEntity<>("Tag no existe.", HttpStatus.BAD_REQUEST);
		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if (!opProblem.isPresent())
			return new ResponseEntity<>("Problema no existe.", HttpStatus.BAD_REQUEST);
		Problem problem = opProblem.get();
		Tag tag = opTag.get();

		tag.removeProblem(problem);
		problem.removeTag(tag);

		tagRepository.save(tag);

		return new ResponseEntity<>("Tag Desasociado correctamente.", HttpStatus.ACCEPTED);
	}
	
}
