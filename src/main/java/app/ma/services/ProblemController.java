package app.ma.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Problem;
import app.ma.repositories.ProblemRepository;

@RestController
public class ProblemController {
	@Autowired	private ProblemRepository problemRepository;
	
	@RequestMapping("/getAllProblems")
	public Iterable<Problem> getAllProblems () {
		
		Iterable<Problem> findAll = problemRepository.findAll();
		return findAll;
	}
	
	@RequestMapping(path="/getProblemById", method=RequestMethod.GET)
	public Problem getProblemByID 
	(
			@RequestHeader Long id) {
		
		Optional<Problem> problem = problemRepository.findById(id);
		if(!problem.isPresent())
			return null;
		return problem.get();
	}
	
	
}
