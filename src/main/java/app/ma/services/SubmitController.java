package app.ma.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RestController;


import app.ma.compositeKey.ProblemContestKey;
import app.ma.compositeKey.ProblemContestUserKey;
import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.MyJob;
import app.ma.entities.ProblemContest;
import app.ma.entities.ProblemContestUser;
import app.ma.entities.Submit;
import app.ma.enums.Language;
import app.ma.enums.Veredict;
import app.ma.objects.HomeworkSubmit;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemContestUserRepository;
import app.ma.repositories.SubmitRepository;

@RestController
@CrossOrigin
public class SubmitController {
	
	@Autowired private SubmitRepository submitRepository;
	@Autowired private ProblemContestUserRepository pcuRepository;
	@Autowired private ProblemContestRepository pcRepository;

    @Autowired
    @Qualifier("singleThreaded")
    private ExecutorService executorService;
	
	
	@RequestMapping("/getAllSubmits")
	public Iterable<Submit> getAllSubmits() {
		Iterable<Submit> findAll = submitRepository.findAll();
		return findAll;
	}
	
	
	@RequestMapping(path="/submit", method = RequestMethod.POST)
	public ResponseEntity<String> submit(
			@RequestParam Long idUser, 
			@RequestParam Long idContest, 
			@RequestParam Long idProblem, 
			@RequestParam String source,
			@RequestParam Language language) {
		Optional<ProblemContestUser> pcuOptional = pcuRepository.findById(new ProblemContestUserKey(idUser, idContest, idProblem));
		ProblemContestUser pcu;
		System.out.println(language);
		if(!pcuOptional.isPresent()) {
			pcu = new ProblemContestUser(idUser, idContest, idProblem);
			ProblemContest pc = pcRepository.findById(new ProblemContestKey(idProblem, idContest)).get();
			pcu.setProblemContest(pc);
			pcuRepository.save(pcu);
		}
		else {
			pcu = pcuOptional.get();			
		}
		
		Submit submit = new Submit();
		submit.setLanguage(language);
		submit.setSourceCodeURL(source);
		submit.setVeredict(Veredict.IN_QUEUE);
		submit.setProblemContestUser(pcu);
		
		submitRepository.save(submit);
		
		
		executorService.execute(new MyJob(submit, this.submitRepository));
		return new ResponseEntity<>("Envio creado correctamente.", HttpStatus.CREATED);
	}
	
	
	@RequestMapping("/getUserSubmits")
	public ResponseEntity<Page<Submit>> getUserSubmits(
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "submitDate") String sortBy,
            @RequestHeader(defaultValue = "true") Boolean ascending,
            @RequestHeader Long userId) {
		Sort sort = Sort.by(sortBy);
		sort = ascending? sort.ascending(): sort.descending();
		Pageable paging = PageRequest.of(pageNo,  pageSize, sort);
		Page<Submit> pagedResult = submitRepository.findByProblemContestUser_User_Id(userId, paging);
        return new ResponseEntity<Page<Submit>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping("/getSubmitsByUserContest")
	public ResponseEntity<Page<Submit>> getSubmitsByUserContest(
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "submitDate") String sortBy,
            @RequestHeader(defaultValue = "true") Boolean ascending,
            @RequestHeader Long userId,
            @RequestHeader(defaultValue = "1") Long contestId) {
		Sort sort = Sort.by(sortBy);
		sort = ascending? sort.ascending(): sort.descending();
		Pageable paging = PageRequest.of(pageNo,  pageSize, sort);
		Page<Submit> pagedResult = submitRepository.findByProblemContestUser_User_IdAndProblemContestUser_ProblemContest_Contest_Id(userId, contestId, paging);
        return new ResponseEntity<Page<Submit>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping("/getContestSubmits")
	public ResponseEntity<Page<Submit>> getContestSubmits(
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "submitDate") String sortBy,
            @RequestHeader(defaultValue = "true") Boolean ascending,
            @RequestHeader Long contestId) {
		Sort sort = Sort.by(sortBy);
		sort = ascending? sort.ascending(): sort.descending();
		Pageable paging = PageRequest.of(pageNo,  pageSize, sort);
		Page<Submit> pagedResult = submitRepository.findByProblemContestUser_ProblemContest_Contest_Id(contestId, paging);
        return new ResponseEntity<Page<Submit>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping("/getSourceCode")
	public ResponseEntity<String> getSourceCode(@RequestHeader Long submitId) {
		Optional<Submit> opSumit= submitRepository.findById(submitId);
		if(!opSumit.isPresent()) return new ResponseEntity<String>("Submit no existe.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(opSumit.get().getSourceCodeURL(), HttpStatus.OK);
	}
	
	@RequestMapping("/getLastProblemAttempt")
	public ResponseEntity<List<Submit>> getLastProblemAttempt(
			@RequestHeader Long userId,
			@RequestHeader(defaultValue = "1") Long contestId,
			@RequestHeader Long problemId) {
		List<Submit> submit= submitRepository.findFirst10ByProblemContestUser_User_IdAndProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_IdOrderBySubmitDateDesc(userId, contestId, problemId);
		if(submit == null) return new ResponseEntity<List<Submit>>(submit, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<List<Submit>>(submit, HttpStatus.OK);
	}
	
	
	@RequestMapping("/getLastSubmitAttempt")
	public ResponseEntity<Submit> getLastSubmitAttempt(
			@RequestHeader Long userId,
			@RequestHeader(defaultValue = "1") Long contestId,
			@RequestHeader Long problemId) {
		Submit submit= submitRepository.findFirst1ByProblemContestUser_User_IdAndProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_IdOrderBySubmitDateDesc(userId, contestId, problemId);
		if(submit == null) return new ResponseEntity<Submit>(submit, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Submit>(submit, HttpStatus.OK);
	}
	
	
	@RequestMapping("/getGradeFromStudent")
	public ResponseEntity<HomeworkSubmit> getGradeFromStudent(
			@RequestHeader Long userId,
			@RequestHeader(defaultValue = "1") Long contestId,
			@RequestHeader Long problemId) {
		Submit submit= submitRepository.findFirst1ByProblemContestUser_User_IdAndProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_IdOrderBySubmitDateDesc(userId, contestId, problemId);
		if(submit == null) return new ResponseEntity<HomeworkSubmit>(new HomeworkSubmit(), HttpStatus.BAD_REQUEST);
		
		ProblemContestUser pcu = pcuRepository.findById(new ProblemContestUserKey(userId, contestId, problemId)).get();
		
		HomeworkSubmit homeworkSubmit = new HomeworkSubmit(submit, pcu.getGrade());
		
		
		return new ResponseEntity<HomeworkSubmit>(homeworkSubmit, HttpStatus.OK);
	}
	
	
	@RequestMapping(path="/saveGrades", method = RequestMethod.POST)
	public ResponseEntity<String> saveGrades(
			@RequestParam Long idContest, 
			@RequestParam Long idProblem, 
			@RequestParam List<Long> studentsId,
			@RequestParam List<Double> grades) {
		
		for(int i = 0; i < studentsId.size(); i++) {
			Optional<ProblemContestUser> opPcu = pcuRepository.findById(new ProblemContestUserKey(studentsId.get(i), idContest, idProblem));
			if(!opPcu.isPresent()) continue;
			ProblemContestUser pcu = opPcu.get();
			pcu.setGrade(grades.get(i));
			pcuRepository.save(pcu);
		}

		return new ResponseEntity<>("Notas actualizadas correctamente.", HttpStatus.CREATED);
	}
	
	
}
