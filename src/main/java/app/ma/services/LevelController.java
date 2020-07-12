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

import com.fasterxml.jackson.annotation.JsonView;

import app.ma.entities.Article;
import app.ma.entities.Level;
import app.ma.entities.User;
import app.ma.objects.JSONView;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.LevelRepository;
import app.ma.repositories.ProblemContestUserRepository;
import app.ma.repositories.UserRepository;

@RestController
public class LevelController {
	@Autowired	private LevelRepository levelRepository;
	@Autowired	private UserRepository userRepository;
	@Autowired	private ArticleRepository articleRepository;
	@Autowired	private ProblemContestUserRepository problemContestUserRepository;
	

	@JsonView(JSONView.LevelSummary.class)
	@CrossOrigin
	@RequestMapping("/getAllLevels")
	public Iterable<Level> getAllLevels () {
		
		Iterable<Level> findAll = levelRepository.findAllByOrderByNumberAsc();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getLevelById", method=RequestMethod.GET)
	public Level getLevelByUserId 
	(
			@RequestHeader Long id) {
		
		Optional<Level> level = levelRepository.findById(id);
		if(!level.isPresent())
			return null;
		return level.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getLevelByUserId", method=RequestMethod.GET)
	public ResponseEntity<Long> getLevelById 
	(
			@RequestHeader Long id) {
		Long levelNumber = 1l;
		Optional<User> opUser = userRepository.findById(id);
		if (!opUser.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		User user = opUser.get();

		boolean hasToChangeLevel = user.getLevel() == null;
		if (!hasToChangeLevel) {
			Level cur = user.getLevel();
			levelNumber = cur.getNumber();
			int problems = cur.getProblems().getProblems().size();
			int problemsSolved = problemContestUserRepository.findSolvedProblems(cur.getProblems().getId(), user.getId()).size();
			if (problemsSolved == problems) {
				hasToChangeLevel = true;
				levelNumber++;
				if(levelNumber>levelRepository.count())
					hasToChangeLevel=false;
			}
		}
		
		if(hasToChangeLevel) {
			user.setLevel(levelRepository.findByNumber(levelNumber));
			userRepository.save(user);
		}

		return new ResponseEntity<>(levelNumber, HttpStatus.ACCEPTED);
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
