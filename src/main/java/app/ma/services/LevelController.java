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

import app.ma.compositeKey.UserCourseKey;
import app.ma.compositeKey.UserStageKey;
import app.ma.entities.Article;
import app.ma.entities.Level;
import app.ma.entities.Stage;
import app.ma.entities.User;
import app.ma.entities.UserCourse;
import app.ma.entities.UserStage;
import app.ma.objects.JSONView;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.LevelRepository;
import app.ma.repositories.ProblemContestUserRepository;
import app.ma.repositories.StageRepository;
import app.ma.repositories.UserRepository;
import app.ma.repositories.UserStageRepository;

@RestController
public class LevelController {
	@Autowired	private LevelRepository levelRepository;
	@Autowired	private StageRepository stageRepository;
	@Autowired	private UserStageRepository userStageRepository;
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
	@RequestMapping("/getAllStages")
	public Iterable<Stage> getAllStages () {
		
		Iterable<Stage> findAll = stageRepository.findAllByOrderByNumberAsc();
		return findAll;
	}
	
	@JsonView(JSONView.LevelSummary.class)
	@CrossOrigin
	@RequestMapping("/getAllLevelsByStage")
	public Iterable<Level> getAllLevelsByStage (
			@RequestHeader Long id) {
		Iterable<Level> findAll = levelRepository.findByStage_IdOrderByNumberAsc(id);
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
	@RequestMapping(path="/getLevelByUserIdAndStageId", method=RequestMethod.GET)
	public ResponseEntity<Long> getLevelByUserIdAndStageId 
	(
			@RequestHeader Long userId,
			@RequestHeader Long stageId) {
		Long levelNumber = 1l;
		Optional<User> opUser = userRepository.findById(userId);
		if (!opUser.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		User user = opUser.get();
		Optional<Stage> opStage = stageRepository.findById(stageId);
		if (!opStage.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		Stage stage = opStage.get();

		UserStage userStage;
		UserStageKey key = new UserStageKey(stage.getId(), user.getId());
		if (!userStageRepository.existsById(key)) {
			userStage = new UserStage();
			userStage.setId(key);
			userStage.setStage(stage);
			userStage.setUser(user);
			userStage.setLevelNumber(levelNumber);
		} else {
			userStage = userStageRepository.findById(key).get();

			Optional<Level> opLevel = levelRepository.findByStage_IdAndNumber(stageId, userStage.getLevelNumber());
			if (!opLevel.isPresent())
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			Level cur = opLevel.get();
			levelNumber = cur.getNumber();
			int problems = cur.getProblems().getProblems().size();
			int problemsSolved = problemContestUserRepository
					.findSolvedProblems(cur.getProblems().getId(), user.getId()).size();
			if (problemsSolved == problems) {
				levelNumber++;
				if (levelNumber < levelRepository.countByStage_Id(stageId))
					userStage.setLevelNumber(levelNumber);
			}

		}

		userStageRepository.save(userStage);


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
