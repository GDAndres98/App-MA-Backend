package app.ma.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.ma.compositeKey.ProblemContestUserKey;
import app.ma.entities.Article;
import app.ma.entities.ProblemContestUser;
import app.ma.entities.Tag;

@Repository
public interface ProblemContestUserRepository extends CrudRepository<ProblemContestUser, ProblemContestUserKey> {
	
	public Iterable<ProblemContestUser> findByProblemContest_Contest_Id(Long id);

	public Iterable<Long> findByProblemContest_Contest_IdAndUser_IdAndSolDateNotNull(Long contestId,Long userId);
	
	@Query("select i.problemContest.problem.id from ProblemContestUser i where i.problemContest.contest.id = :contestId and i.user.id = :userId and i.solDate IS NOT NULL")
	public  List<Long>  findSolvedProblems(@Param("contestId") Long contestId, @Param("userId") Long userId);

	
}