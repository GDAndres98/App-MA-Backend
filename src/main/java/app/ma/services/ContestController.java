package app.ma.services;

import java.util.ArrayList;
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
import app.ma.entities.Problem;
import app.ma.objects.ContestStats;
import app.ma.objects.ProblemInContest;
import app.ma.repositories.ContestRepository;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.SubmitRepository;

@RestController
@CrossOrigin
public class ContestController {
	
	@Autowired 	private ContestRepository contestRepository;
	@Autowired 	private SubmitRepository submitRepository;
	@Autowired 	private ProblemRepository problemRepository;
	@Autowired 	private ProblemContestRepository problemContestRepository;
	
	
	@RequestMapping(path="/getAllProblemsFromContest", method=RequestMethod.GET)
	public ResponseEntity<List<Problem>> getAllProblemsFromContest
	(
			@RequestHeader Long id) {
		List<Problem> problems = problemRepository.findByProblemContest_Contest_Id(id);
		if(problems.size() == 0)
			return new ResponseEntity<List<Problem>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		 return new ResponseEntity<List<Problem>>(problems, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(path="/getContestById", method=RequestMethod.GET)
	public ResponseEntity<ContestStats> testf(
			@RequestHeader Long id) {
		Optional<Contest> contest = contestRepository.findById(id);
		if(!contest.isPresent())
			return new ResponseEntity<ContestStats>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		
		ContestStats contestStats = new ContestStats(contest.get(), submitRepository);
		
		return new ResponseEntity<ContestStats>(contestStats, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(path="/getScoreboardByContestId", method=RequestMethod.GET)
	public ResponseEntity<ContestStats> getScoreboardByContestId(
			@RequestHeader Long id) {
		Optional<Contest> contest = contestRepository.findById(id);
		if(!contest.isPresent())
			return new ResponseEntity<ContestStats>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		
		ContestStats contestStats = new ContestStats(contest.get(), submitRepository);
		
		return new ResponseEntity<ContestStats>(contestStats, new HttpHeaders(), HttpStatus.OK);
	}
	
	
}
