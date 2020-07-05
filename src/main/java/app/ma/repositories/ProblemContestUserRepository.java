package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.compositeKey.ProblemContestUserKey;
import app.ma.entities.ProblemContestUser;

@Repository
public interface ProblemContestUserRepository extends CrudRepository<ProblemContestUser, ProblemContestUserKey> {
	
	public Iterable<ProblemContestUser> findByProblemContest_Contest_Id(Long id);
	
}