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
import org.springframework.web.bind.annotation.RestController;


import app.ma.compositeKey.ProblemContestKey;
import app.ma.compositeKey.ProblemContestUserKey;
import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.ProblemContestUser;
import app.ma.entities.Submit;
import app.ma.enums.Language;
import app.ma.enums.Veredict;
import app.ma.repositories.ProblemContestUserRepository;
import app.ma.repositories.SubmitRepository;

@RestController
@CrossOrigin
public class SubmitController {
	
	@Autowired private SubmitRepository submitRepository;
	@Autowired private ProblemContestUserRepository pcuRepository;
	
	
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
		if(!pcuOptional.isPresent()) {
			pcu = new ProblemContestUser(idUser, idContest, idProblem);
			pcuRepository.save(pcu);
		}
		else
			pcu = pcuOptional.get();
		
		
		Submit submit = new Submit();
		submit.setLanguage(language);
		submit.setSourceCodeURL(source);
		submit.setVerdict(Veredict.In_Queue);
		submit.setProblemContestUser(pcu);
		
		submitRepository.save(submit);
		
		return new ResponseEntity<>("Envio creado correctamente.", HttpStatus.CREATED);
	}
	
	
	@RequestMapping("/getUserSubmits")
	public ResponseEntity<Page<Submit>> getUserSubmits(
            @RequestHeader(defaultValue = "0") Integer pageNo, 
            @RequestHeader(defaultValue = "10") Integer pageSize,
            @RequestHeader(defaultValue = "submitDate") String sortBy,
            @RequestHeader Long userId) {
		Pageable paging = PageRequest.of(pageNo,  pageSize, Sort.by(sortBy));
		Page<Submit> pagedResult = submitRepository.findByProblemContestUser_User_Id(userId, paging);
        return new ResponseEntity<Page<Submit>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}
	
}
