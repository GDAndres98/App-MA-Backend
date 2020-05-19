package app.ma.services;

import java.util.Date;
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
import app.ma.entities.User;
import app.ma.repositories.ArticleRepository;

@RestController
public class ArticleController {
	@Autowired
	private ArticleRepository articleRepostory;
	
	@RequestMapping("/getAllArticles")
	public Iterable<Article> getAllArticles () {
		
		Iterable<Article> findAll = articleRepostory.findAll();
		return findAll;
	}
	
	@RequestMapping(path="/getArticleByID", method=RequestMethod.GET)
	public Article getArticleByID 
	(
			@RequestHeader Long id) {
		
		Optional<Article> article = articleRepostory.findById(id);
		if(!article.isPresent())
			return null;
		return article.get();
	}
	
	@RequestMapping(path="/createArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createArticle 
	(
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Date		dateWritten) {
		
		Article article = new Article();
		
		article.setTitle(title);
		article.setAuthor(author);
		article.setMarkdownURL(markdownURL);
		article.setDateWritten(dateWritten);
		
		articleRepostory.save(article);
		
		return new ResponseEntity<>("Articulo creado correctamente.", HttpStatus.CREATED);
	}
}
