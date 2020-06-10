package app.ma.services;

import java.util.Optional;

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
import app.ma.entities.Problem;
import app.ma.entities.TestCase;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.TestCaseRepository;

@RestController
public class ProblemController {
	@Autowired	private ProblemRepository problemRepository;
	@Autowired	private TestCaseRepository testCaseRepository;
	
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
	@RequestMapping(path="/getProblemById", method=RequestMethod.GET)
	public Problem getProblemByID 
	(
			@RequestHeader Long id) {
		
		Optional<Problem> problem = problemRepository.findById(id);
		if(!problem.isPresent())
			return null;
		return problem.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getAllTestCasesByProblemId", method=RequestMethod.GET)
	public Iterable<TestCase> getAllTestCasesByProblemId
	(
			@RequestHeader Long problemId) {
		Iterable<TestCase> findAll = testCaseRepository.findByProblem_Id(problemId);
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/createProblem", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createProblem 
	(
			@RequestParam String 	title		, 
			@RequestParam String 	author		, 
			@RequestParam String 	markdownURL	, 
			@RequestParam Long 		timeLimit	, 
			@RequestParam Long  	memoryLimit) {
		
		Problem problem = new Problem();
		
		problem.setTitle(title);
		problem.setAuthor(author);
		problem.setMarkdown(markdownURL);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		
		problemRepository.save(problem);
		
		return new ResponseEntity<>("Problema creado correctamente.", HttpStatus.CREATED);
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
	

}
