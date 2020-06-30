package app.ma.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Submit;
import app.ma.entities.Tag;

@Repository
public interface SubmitRepository extends PagingAndSortingRepository<Submit, Long> {
	public Page<Submit> findByProblemContestUser_User_Id(Long id, Pageable page);
	public Page<Submit> findByProblemContestUser_ProblemContest_Contest_Id(Long id, Pageable page);
	public List<Submit> findFirst10ByProblemContestUser_User_IdAndProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_IdOrderBySubmitDateDesc(Long userId, Long contestId, Long problemId);
	
}