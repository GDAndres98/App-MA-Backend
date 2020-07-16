package app.ma.services;

import java.time.Duration;
import java.time.LocalTime;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.ma.compositeKey.ProblemContestKey;
import app.ma.entities.Article;
import app.ma.entities.Contest;
import app.ma.entities.Problem;
import app.ma.entities.ProblemContest;
import app.ma.entities.Section;
import app.ma.entities.Tag;
import app.ma.entities.TestCase;
import app.ma.repositories.ContestRepository;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.TagRepository;
import app.ma.repositories.TestCaseRepository;
import reactor.core.publisher.Flux;

@RestController
public class ProblemController {
	@Autowired	private TagRepository tagRepository;
	@Autowired	private ProblemRepository problemRepository;
	@Autowired	private TestCaseRepository testCaseRepository;
	@Autowired 	private ContestRepository contestRepository;
	@Autowired 	private ProblemContestRepository problemContestRepository;

	
	@CrossOrigin
	@RequestMapping("/getAllProblems")
	public Iterable<Problem> getAllProblems () {
		
		Iterable<Problem> findAll = problemRepository.findAll();
		return findAll;
	}
	
	@RequestMapping("/getAllTC")
	public Iterable<TestCase> getAllTC () {
		
		Iterable<TestCase> findAll = testCaseRepository.findAll();
		return findAll;
	}
	
	
	@CrossOrigin
	@RequestMapping(path="/getAllProblemPagination", method=RequestMethod.GET)
    public ResponseEntity<Page<Problem>> getAllProblemPagination(
                       @RequestHeader(defaultValue = "0") Integer pageNo, 
                       @RequestHeader(defaultValue = "10") Integer pageSize,
                       @RequestHeader(defaultValue = "id") String sortBy) 
    {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<Problem> pagedResult = problemRepository.findAll(paging);
         
//        if(pagedResult.hasContent()) {
//            return new ResponseEntity<List<Article>>(pagedResult.getContent(), new HttpHeaders(), HttpStatus.OK); 
//        } else {
//            return new ResponseEntity<List<Article>>(new ArrayList<Article>(), new HttpHeaders(), HttpStatus.OK); 
//        }
        return new ResponseEntity<Page<Problem>>(pagedResult, new HttpHeaders(), HttpStatus.OK);

    }
	
	@CrossOrigin
	@RequestMapping(path="/getSearchProblem", method=RequestMethod.GET)
	public ResponseEntity<List<Problem>> getSearchProblem
	(
			@RequestHeader String prefix) {
		List<Problem> problem= problemRepository.findFirst5ByTitleContainsIgnoreCaseOrderByTitleAsc(prefix);
		 return new ResponseEntity<List<Problem>>(problem, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	
	@CrossOrigin
	@RequestMapping(path="/getProblemById", method=RequestMethod.GET)
	public ResponseEntity<Problem> getProblemByID 
	(
			@RequestHeader Long id) {
		
		Optional<Problem> problem = problemRepository.findById(id);
		if(!problem.isPresent())
			return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		 return new ResponseEntity<Problem>(problem.get(), new HttpHeaders(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getAllTestCasesByProblemId", method=RequestMethod.GET)
	public Iterable<TestCase> getAllTestCasesByProblemId
	(
			@RequestHeader Long problemId) {
		Iterable<TestCase> findAll = testCaseRepository.findByProblem_IdOrderByOrderTestCase(problemId);
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/setTestCases", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> setTestCases
	(
			@RequestParam Long 		problemId, 
			@RequestParam List<String>  input,
			@RequestParam List<String>  output,
			@RequestParam List<Long> 	dificulty) {
		
		System.out.println(input);
		Optional<Problem> opProblem = this.problemRepository.findById(problemId);
		if(!opProblem.isPresent())
			return new ResponseEntity<String>("Problema no existe", new HttpHeaders(), HttpStatus.NOT_FOUND);
		
		Problem problem = opProblem.get();
		
		for(TestCase tc: problem.getTestCases())
			this.testCaseRepository.delete(tc);
		problem.setTestCases(new HashSet<>());
		
		for(int i = 0; i < input.size(); i++) {
			TestCase tc = new TestCase();
			tc.setTcInputURL(input.get(i));
			tc.setTcOutputURL(output.get(i));
			tc.setTcDifficulty(dificulty.get(i));
			tc.setOrderTestCase((long)i);
			tc.setProblem(problem);
			this.testCaseRepository.save(tc);
			problem.addTestCase(tc);
		}
		
		this.problemRepository.save(problem);
		
		return new ResponseEntity<>("Casos de prueba actualizados correctamente.", HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@RequestMapping(path="/createProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createProblem 
	(
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Long 		timeLimit	, 
			@RequestParam Long  	memoryLimit,
			@RequestParam(required = false) List<Long>  tags) {
		
		Problem problem = new Problem();
		
		problem.setTitle(title);
		problem.setAuthor(author);
		problem.setMarkdown(markdownURL);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		
		problemRepository.save(problem);
		
		Contest generalContest = contestRepository.findById(1l).get();	
		
		
		ProblemContest problemContest = new ProblemContest();
		problemContest.setProblem(problem);
		problemContest.setContest(generalContest);
		problemContest.setId(new ProblemContestKey(problem.getId(), generalContest.getId()));
		
		problem.addProblemContest(problemContest);
		generalContest.addProblemContest(problemContest);
		problemContestRepository.save(problemContest);
		
		
		if(tags != null) {
			Iterable<Tag> tagsOp = tagRepository.findAllById(tags);
			
			for(Tag e: tagsOp){
				problem.addTag(e);
				e.addProblem(problem);
				tagRepository.save(e);
			}
		}
		problemRepository.save(problem);
		
		
		return new ResponseEntity<>("Problema creado correctamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/updateProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> updateProblem 
	(
			@RequestParam Long 		id			, 
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Long 		timeLimit	, 
			@RequestParam Long  	memoryLimit,
			@RequestParam(required = false) List<Long>  tags) {
		
		Optional<Problem> problemOp = this.problemRepository.findById(id);
		if(!problemOp.isPresent())
			return new ResponseEntity<String>("Problema no existe", new HttpHeaders(), HttpStatus.NOT_FOUND);
		Problem problem = problemOp.get();
		problem.setAuthor(author);
		problem.setTitle(title);
		problem.setMarkdown(markdownURL);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		problem.setTags(new HashSet<Tag>());

		if(tags != null) {
			Iterable<Tag> tagsOp = tagRepository.findAllById(tags);
			
			for(Tag e: tagsOp){
				problem.addTag(e);
				e.addProblem(problem);
				tagRepository.save(e);
			}
		}
		try {			
			problemRepository.save(problem);
		} catch (Exception e) {
			return new ResponseEntity<String>("Falló en actualizar problema", new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("Problema actualizado correctamente.", HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(path="/removeProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeProblem 
	(
			@RequestParam Long 		problemId	) {

		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if (!opProblem.isPresent())
			return new ResponseEntity<>("Problema no valido.", HttpStatus.BAD_REQUEST);
		Problem problem = opProblem.get();
		
		problemRepository.delete(problem);
		return new ResponseEntity<>("Problema eliminado correctamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/addTestCase", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addTestCase 
	(
			@RequestParam Long 		problemId	, 
			@RequestParam String 	tcInput		, 
			@RequestParam String 	tcOutput	, 
			@RequestParam Long  	tcDifficulty) {

		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if (!opProblem.isPresent())
			return new ResponseEntity<>("Problema no valido.", HttpStatus.BAD_REQUEST);
		Problem problem = opProblem.get();

		TestCase testCase = new TestCase();

		testCase.setProblem(problem);
		testCase.setTcInputURL(tcInput);
		testCase.setTcOutputURL(tcOutput);
		testCase.setTcDifficulty(tcDifficulty);

		testCaseRepository.save(testCase);

		return new ResponseEntity<>("Caso de Prueba añadido correctamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/removeTestCase", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeTestCase 
	(
			@RequestParam Long 		testCaseId	) {

		Optional<TestCase> opTestCase = testCaseRepository.findById(testCaseId);
		if (!opTestCase.isPresent())
			return new ResponseEntity<>("Caso de prueba no valido.", HttpStatus.BAD_REQUEST);

		testCaseRepository.deleteById(testCaseId);
		return new ResponseEntity<>("Caso de Prueba eliminado correctamente.", HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@RequestMapping(path="/getProblemsWithTags", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Problem>>  getArticlesWithTags
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
		
		Page<Problem> pagedResult = problemRepository.findAllProblemsWithTags(tags, Long.valueOf(tags.size()), paging);
		
		 return new ResponseEntity<Iterable<Problem>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}

}
