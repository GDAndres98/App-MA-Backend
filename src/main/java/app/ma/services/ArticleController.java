package app.ma.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
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
import app.ma.entities.Section;
import app.ma.entities.Tag;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.TagRepository;

@RestController
public class ArticleController {
	@Autowired	private ArticleRepository articleRepository;
	@Autowired	private TagRepository tagRepository;
	
	@CrossOrigin
	@RequestMapping("/getAllArticles")
	public Iterable<Article> getAllArticles () {
		
		Iterable<Article> findAll = articleRepository.findAll();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getArticleById", method=RequestMethod.GET)
	public ResponseEntity<Article> getArticleByID 
	(
			@RequestHeader Long id) {
		Optional<Article> article = articleRepository.findById(id);
		if(!article.isPresent())
				return new ResponseEntity<Article>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);;
		 return new ResponseEntity<Article>(article.get(), new HttpHeaders(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getAllArticlesPagination", method=RequestMethod.GET)
    public ResponseEntity<Page<Article>> getAllArticlesPagination(
                       @RequestHeader(defaultValue = "0") Integer pageNo, 
                       @RequestHeader(defaultValue = "10") Integer pageSize,
                       @RequestHeader(defaultValue = "id") String sortBy) 
    {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<Article> pagedResult = articleRepository.findAll(paging);
         
//        if(pagedResult.hasContent()) {
//            return new ResponseEntity<List<Article>>(pagedResult.getContent(), new HttpHeaders(), HttpStatus.OK); 
//        } else {
//            return new ResponseEntity<List<Article>>(new ArrayList<Article>(), new HttpHeaders(), HttpStatus.OK); 
//        }
        return new ResponseEntity<Page<Article>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
 
    }
	
	@CrossOrigin
	@RequestMapping(path="/createArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createArticle 
	(
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Date		dateWritten ,
			@RequestParam(required = false) List<Long>  tags) {
		
		
		
		Article article = new Article();
		
		article.setTitle(title);
		article.setAuthor(author);
		article.setMarkdown(markdownURL);
		article.setDateWritten(dateWritten);
		
		articleRepository.save(article);
		
		if(tags != null) {
			Iterable<Tag> tagsOp = tagRepository.findAllById(tags);
			
			for(Tag e: tagsOp){
				article.addTag(e);
				e.addArticle(article);
				tagRepository.save(e);
			}
		}
		articleRepository.save(article);
		
		return new ResponseEntity<>("Articulo creado correctamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/updateArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> updateArticle 
	(
			@RequestParam Long 		id			, 
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Date		dateWritten ,
			@RequestParam(required = false) List<Long>  tags) {
		
		Optional<Article> articleOp = this.articleRepository.findById(id);
		if(!articleOp.isPresent())
			return new ResponseEntity<String>("Artículo no existe", new HttpHeaders(), HttpStatus.NOT_FOUND);
		Article article = articleOp.get();
		article.setAuthor(author);
		article.setDateWritten(dateWritten);
		article.setTitle(title);
		article.setMarkdown(markdownURL);
		article.setTags(new HashSet<Tag>());

		if(tags != null) {
			Iterable<Tag> tagsOp = tagRepository.findAllById(tags);
			
			for(Tag e: tagsOp){
				article.addTag(e);
				e.addArticle(article);
				tagRepository.save(e);
			}
		}
		try {			
			articleRepository.save(article);
		} catch (Exception e) {
			return new ResponseEntity<String>("Falló en actualizar artículo", new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("Articulo actualizado correctamente.", HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(path="/deleteArticle", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> deleteArticle 
	(
			@RequestParam Long 		id) {
		
		Optional<Article> articleOp = this.articleRepository.findById(id);
		if(!articleOp.isPresent())
			return new ResponseEntity<String>("Artículo no existe", new HttpHeaders(), HttpStatus.NOT_FOUND);	
		Article article = articleOp.get();
		
		for (Tag b : article.getTags())
			b.deleteArticle(article);
		for (Section b : article.getSections())
			b.deleteArticle(article);
		
		this.articleRepository.save(article);
		this.articleRepository.deleteById(id);
		
		return new ResponseEntity<>("Articulo eliminado correctamente.", HttpStatus.OK);
	}
	
	
	
	
	
	
	@CrossOrigin
	@RequestMapping(path="/getSearchArticle", method=RequestMethod.GET)
	public ResponseEntity<List<Article>> getSearchArticle
	(
			@RequestHeader String prefix) {
		List<Article> article = articleRepository.findFirst5ByTitleContainsIgnoreCaseOrderByTitleAsc(prefix);
		 return new ResponseEntity<List<Article>>(article, new HttpHeaders(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getArticlesWithTags", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Article>>  getArticlesWithTags
	(
			@RequestHeader List<Long> tagsId,
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "id") String sortBy) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
		
		Set<Tag> tags= new HashSet<Tag>();
		for(Long e: tagsId) {
			Optional<Tag> tag = tagRepository.findById(e);
			tags.add(tag.get());
		}
		
		Page<Article> pagedResult = articleRepository.findAllArticlesWithTags(tags, Long.valueOf(tags.size()), paging);
		
		 return new ResponseEntity<Iterable<Article>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
}
