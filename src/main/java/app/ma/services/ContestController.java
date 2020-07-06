package app.ma.services;

import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.fasterxml.jackson.annotation.JsonView;

import app.ma.entities.Article;
import app.ma.compositeKey.ProblemContestKey;
import app.ma.entities.Contest;
import app.ma.entities.Problem;
import app.ma.entities.ProblemContest;
import app.ma.entities.Tag;
import app.ma.objects.ContestScoreboard;
import app.ma.objects.ContestStats;
import app.ma.objects.JSONView;
import app.ma.repositories.ContestRepository;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemContestUserRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.SubmitRepository;

@RestController
@CrossOrigin
public class ContestController {
	
	@Autowired 	private ContestRepository contestRepository;
	@Autowired 	private SubmitRepository submitRepository;
	@Autowired 	private ProblemRepository problemRepository;
	@Autowired 	private ProblemContestRepository problemContestRepository;
	@Autowired 	private ProblemContestUserRepository problemContestUserRepository;
	
	
	@RequestMapping(path = "/getScoreboard", method = RequestMethod.GET)
	public ResponseEntity<ContestScoreboard> getScoreboard(
			@RequestHeader Long id) {

		Optional<Contest> contest = contestRepository.findById(id);
		if (!contest.isPresent())
			return new ResponseEntity<ContestScoreboard>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

		ContestScoreboard contestScoreBoard = new ContestScoreboard(contest.get(), problemContestUserRepository);

		return new ResponseEntity<ContestScoreboard>(contestScoreBoard, new HttpHeaders(), HttpStatus.OK);
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

	@CrossOrigin
	@RequestMapping(path="/createContest", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createContest
	(
			@RequestParam String 	name		, 
			@RequestParam Boolean 	isPrivate	,
			@RequestParam(required = false) String 	password,
			@RequestParam Date 		startTime	, 
			@RequestParam Date 		endTime	, 
			@RequestParam(required = false) List<Long>  problems) {
		
		Contest contest = new Contest();
		contest.setName(name);
		if(isPrivate) {
			contest.setPrivate(true);
			contest.setPassword(password);
		}
		
		if(startTime.after(endTime)) 
			return new ResponseEntity<String>("ERROR: Hora de inicio despues de la hora final ", new HttpHeaders(), HttpStatus.CONFLICT);
		contest.setStartTime(startTime);
		contest.setEndTime(endTime);
		contest.setVisible(true);
		contest.setPartialVerdict(false);
		
		this.contestRepository.save(contest);
		
		Map<Long, String> map = new HashMap<>();
		for(int i = 0; i < problems.size(); i++)
			map.put(problems.get(i), (char)('A' + i) + "");
		
		
		Iterable<Problem> problemList = this.problemRepository.findAllById(problems);
				
		for(Problem p: problemList) {
			ProblemContest problemContest = new ProblemContest();
			problemContest.setContest(contest);
			problemContest.setProblem(p);
			problemContest.setId(new ProblemContestKey(p.getId(), contest.getId()));
			problemContest.setLetter(map.get(p.getId()));
			this.problemContestRepository.save(problemContest);
		}
		this.contestRepository.save(contest);
		
		
		return new ResponseEntity<>("Competencia creada correctamente.", HttpStatus.CREATED);
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
	
	@JsonView(JSONView.ContestSummary.class)
	@RequestMapping(path="/getRunningContests", method=RequestMethod.GET)
	public ResponseEntity<List<Contest>> getRunningContests() {
		
		Date currentDate = new Date();
		 
		List<Contest> pagedResult = contestRepository.findByStartTimeLessThanAndEndTimeGreaterThanAndIsVisibleIsTrue(currentDate,currentDate);
		
		return new ResponseEntity<List<Contest>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
	@JsonView(JSONView.ContestSummary.class)
	@RequestMapping(path="/getFutureContests", method=RequestMethod.GET)
	public ResponseEntity<List<Contest>> getFutureContests() {
		
		Date currentDate = new Date();
		 
		List<Contest> pagedResult = contestRepository.findByStartTimeGreaterThanAndIsVisibleIsTrue(currentDate);
		
		return new ResponseEntity<List<Contest>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(path="/getPastContests", method=RequestMethod.GET)
	public ResponseEntity<Page<Contest>> getPastContests(
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "id") String sortBy) {
		
		Date currentDate = new Date();
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<Contest> pagedResult = contestRepository.findByEndTimeLessThanAndIsVisibleIsTrue(currentDate, paging);
		
		return new ResponseEntity<Page<Contest>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
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
