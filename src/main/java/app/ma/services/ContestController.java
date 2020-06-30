package app.ma.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Contest;
import app.ma.entities.Post;
import app.ma.entities.Problem;
import app.ma.repositories.ContestRepository;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemRepository;

@RestController
@CrossOrigin
public class ContestController {
	
	@Autowired 	private ContestRepository contestRepository;
	@Autowired 	private ProblemRepository problemRepository;
	@Autowired 	private ProblemContestRepository problemContestRepository;
	
	@RequestMapping(path="/getContestById", method=RequestMethod.GET)
	public ResponseEntity<Contest> getContestId
	(
			@RequestHeader Long id) {
		Optional<Contest> contest = contestRepository.findById(id);
		System.out.println(contest.get().getStartTime());
		System.out.println(contest.get().getEndTime());
		if(!contest.isPresent())
			return new ResponseEntity<Contest>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		 return new ResponseEntity<Contest>(contest.get(), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(path="/getAllProblemsFromContest", method=RequestMethod.GET)
	public ResponseEntity<List<Problem>> getAllProblemsFromContest
	(
			@RequestHeader Long id) {
		List<Problem> problems = problemRepository.findByProblemContest_Contest_Id(id);
		if(problems.size() == 0)
			return new ResponseEntity<List<Problem>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		 return new ResponseEntity<List<Problem>>(problems, new HttpHeaders(), HttpStatus.OK);
	}
	

}
