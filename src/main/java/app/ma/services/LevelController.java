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
import app.ma.entities.Level;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.LevelRepository;

@RestController
public class LevelController {
	@Autowired	private LevelRepository levelRepository;
	@Autowired	private ArticleRepository articleRepository;
	
	@CrossOrigin
	@RequestMapping("/getAllLevels")
	public Iterable<Level> getAllLevels () {
		
		Iterable<Level> findAll = levelRepository.findAllByOrderByNumberAsc();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getLevelById", method=RequestMethod.GET)
	public Level getLevelById 
	(
			@RequestHeader Long id) {
		
		Optional<Level> level = levelRepository.findById(id);
		if(!level.isPresent())
			return null;
		return level.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/addArticleToLevel", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addArticleToLevel
	(
			@RequestParam Long 		articleId,  
			@RequestParam Long 		LevelId) {
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if(!opArticle.isPresent()) return new ResponseEntity<>("Articulo no existe.", HttpStatus.BAD_REQUEST);
		Optional<Level> opLevel = levelRepository.findById(LevelId);
		if(!opLevel.isPresent()) return new ResponseEntity<>("Nivel no existe.", HttpStatus.BAD_REQUEST);
		Level level = opLevel.get();
		Article article = opArticle.get();

		level.addArticle(article);
		article.setLevel(level);
		
		levelRepository.save(level);

		return new ResponseEntity<>("Articulo Agregado correctamente.", HttpStatus.ACCEPTED);
	}
	
	
}
