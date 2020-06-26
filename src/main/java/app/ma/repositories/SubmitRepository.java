package app.ma.repositories;


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
}